package com.zoomout.discovery.apis;

import com.asyncapi.v2.binding.ChannelBinding;
import com.asyncapi.v2.binding.OperationBinding;
import com.asyncapi.v2.model.channel.ChannelItem;
import com.asyncapi.v2.model.channel.operation.Operation;
import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import com.google.common.collect.ImmutableMap;
import com.zoomout.discovery.ZoomOutDiscovery;
import com.zoomout.discovery.types.AmqpConsumerData;
import com.zoomout.discovery.types.AsyncAPI;
import com.zoomout.discovery.types.Components;
import com.zoomout.discovery.types.Constants;
import com.zoomout.discovery.types.ConsumerData;
import com.zoomout.discovery.types.message.Message;
import com.zoomout.discovery.types.message.PayloadReference;
import com.zoomout.discovery.utils.AppMetadataHolder;
import com.zoomout.discovery.utils.DefaultSchemasService;
import com.zoomout.discovery.utils.ObjectMapperUtils;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.Metadata;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import com.zoomout.model.api.ApiDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Discover API AsyncApi - Rabbit MQ
 * {@link org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor}
 * {@link RabbitListenerEndpointRegistrar}
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(RabbitListenerEndpointRegistry.class)
//@ConditionalOnBean(OpenApiWebMvcResource.class)
public class RabbitMqAsyncApiDiscovery implements ZoomOutDiscovery {
	
	private final RabbitProperties rabbitProperties;
	
	private final AppMetadataHolder appMetadataHolder;
	
	private final Map<String, RabbitListenerEndpointRegistry> registryMap;
	
	private final Map<String, Binding> bindingMap;
	
	private final Map<String, Exchange> exchangeMap;
	
	private final Map<String, Queue> queueMap;
	private final RabbitListenerAnnotationBeanPostProcessor rabbitListenerAnnotationBeanPostProcessor;
	
	private final DefaultSchemasService schemasService;
	
	@Override
	public KindType getType() {
		return KindType.API;
	}
	
	@Override
	public SpecType getSpecType() {
		return SpecType.ASYNC_API;
	}
	
	@Override
	public List<Element> discovery() {
		List<Element> apis = new ArrayList<>();
		
		if (isRelevant()) {
			
			Map<String, ApiDefinition> apiDefinitionMap = new HashMap<>();
			SpecElement specElement = SpecElement.builder()
					.type(SpecType.ASYNC_API).build();
			Element asyncApiElement = Element.builder()
					.kind(KindType.API)
					.metadata(Metadata.builder()
							.name(getApiName())
							.additional(Map.of("api", ObjectMapperUtils.valueToTree(apiDefinitionMap))).build())
					.spec(specElement).build();
			
			List<ConsumerData> consumerDataList = new ArrayList<>();
			
			registryMap.values().forEach(rabbitListenerEndpointRegistry -> {
				rabbitListenerEndpointRegistry.getListenerContainers().forEach(messageListenerContainer -> {
					String[] queueNames = ((SimpleMessageListenerContainer) messageListenerContainer).getQueueNames();
					MessagingMessageListenerAdapter messageListener = (MessagingMessageListenerAdapter) messageListenerContainer.getMessageListener();
					try {
						Method getHandlerAdapterMethod = MessagingMessageListenerAdapter.class.getDeclaredMethod("getHandlerAdapter");
						getHandlerAdapterMethod.setAccessible(true);
						HandlerAdapter result = (HandlerAdapter) getHandlerAdapterMethod.invoke(messageListener);
						Method handleMethod = result.getMethodFor(null);
						Parameter[] parameters = handleMethod.getParameters();
						
						String queueName = queueNames[0];
						Optional<Binding> exchange = getBindingByQueueName(queueName);
						
						if (exchange.isPresent()) {
							Binding binding = exchange.get();
							AmqpConsumerData consumerData = AmqpConsumerData.amqpConsumerDataBuilder()
									.queueName(queueName)
									.exchangeName(binding.getExchange())
									.routingKey(binding.getRoutingKey())
									.build();
							
							if (parameters.length == 1) {
								consumerData.setPayloadType(parameters[0].getType());
								consumerData.setMethod(handleMethod.getName());
							}
							
							consumerDataList.add(consumerData);
							String name = getExchangeNameByQueueName(queueName);
							apiDefinitionMap.put(name, ApiDefinition.builder()
									.url(name)
									.method(handleMethod.toString()).build());
						}
						
						getHandlerAdapterMethod.setAccessible(false);
					} catch (Exception e) {
						log.error("Failed retrieve method handlers of Rabbit events", e);
					}
				});
			});
			
			Map<String, ChannelItem> channelItemMap = convert(consumerDataList);
			
			Server amqp = Server.builder()
					.protocol("amqp")
					.url(String.format("%s:%s", rabbitProperties.getHost(), rabbitProperties.getPort()))
					.build();
			
			AsyncAPI asyncAPI = AsyncAPI.builder()
					.info(Info.builder()
							.title("amqp")
							.version("1.0.0")
							.build())
					.servers(Map.of("amqp", amqp))
					.channels(channelItemMap)
					.components(Components.builder()
							.schemas(schemasService.getDefinitions())
							.build())
					.build();
			
			specElement.setDefinition(ObjectMapperUtils.toString(asyncAPI));
			
			apis.add(asyncApiElement);
		}
		
		return apis;
	}
	
	private Optional<Binding> getBindingByQueueName(String queueName) {
		return bindingMap.values().stream().filter(binding -> binding.getDestination().equals(queueName))
				.findFirst();
	}
	
	private String getExchangeNameByQueueName(String queueName) {
		return getBindingByQueueName(queueName)
				.map(binding -> String.format("%s_%s_%s", binding.getExchange(), binding.getRoutingKey(), binding.getDestination()))
				.orElse(queueName);
	}
	
	@Override
	public boolean isRelevant() {
		return !CollectionUtils.isEmpty(registryMap);
	}
	
	private ChannelItem buildChannel(List<ConsumerData> consumerDataList) {
		// All bindings in the group are assumed to be the same
		// AsyncApi does not support multiple bindings on a single channel
		Map<String, ? extends ChannelBinding> channelBinding = consumerDataList.get(0).getChannelBinding();
		Map<String, ? extends OperationBinding> operationBinding = consumerDataList.get(0).getOperationBinding();
		
		Operation operation = Operation.builder()
				.message(getMessageObject(consumerDataList))
				.bindings(operationBinding)
				.build();
		
		return ChannelItem.builder()
				.bindings(channelBinding)
				.publish(operation)
				.build();
	}
	
	private Object getMessageObject(List<ConsumerData> consumerDataList) {
		Set<Message> messages = consumerDataList.stream()
				.map(this::buildMessage)
				.collect(toSet());
		
		return messages.size() == 1
				? messages.toArray()[0]
				: ImmutableMap.of(Constants.ONE_OF, messages);
	}
	
	private Message buildMessage(ConsumerData consumerData) {
		Class<?> payloadType = consumerData.getPayloadType();
		String modelName = schemasService.register(payloadType);
		
		return Message.builder()
				.name(payloadType.getName())
				.title(modelName)
				.payload(PayloadReference.fromModelName(modelName))
				.build();
	}
	
	public Map<String, ChannelItem> convert(List<ConsumerData> consumerData) {
		Map<String, List<ConsumerData>> consumerDataGroupedByChannelName = consumerData.stream()
				.filter(this::allFieldsAreNonNull)
				.collect(groupingBy(ConsumerData::getChannelName));
		
		return consumerDataGroupedByChannelName.entrySet().stream()
				.collect(toMap(Map.Entry::getKey, entry -> buildChannel(entry.getValue())));
	}
	
	private boolean allFieldsAreNonNull(ConsumerData consumerData) {
		boolean allNonNull = consumerData.getChannelName() != null
				&& consumerData.getPayloadType() != null
				&& consumerData.getOperationBinding() != null;
		
		if (!allNonNull) {
			log.warn("Some consumer data fields are null - this consumer will not be documented: {}", consumerData);
		}
		
		return allNonNull;
	}
	
	private String getApiName() {
		return String.format("%s-%s", appMetadataHolder.getApplicationName(), getSpecType().getValue());
	}
}

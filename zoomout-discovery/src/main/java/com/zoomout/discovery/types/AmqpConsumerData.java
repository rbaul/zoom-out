package com.zoomout.discovery.types;

import com.asyncapi.v2.binding.amqp.AMQPChannelBinding;
import com.asyncapi.v2.binding.amqp.AMQPOperationBinding;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;

public class AmqpConsumerData extends ConsumerData {
	
	@Builder(builderMethodName = "amqpConsumerDataBuilder")
	public AmqpConsumerData(String queueName, String exchangeName, String routingKey, Class<?> payloadType, Method handleMethod) {
		this.channelName = queueName;
		
		AMQPChannelBinding.ExchangeProperties exchangeProperties = new AMQPChannelBinding.ExchangeProperties();
		exchangeProperties.setName(exchangeName);
		this.channelBinding = ImmutableMap.of("amqp", AMQPChannelBinding.builder()
				.is("routingKey")
				.exchange(exchangeProperties)
				.build());
		
		this.operationBinding = ImmutableMap.of("amqp", AMQPOperationBinding.builder()
				.cc(Collections.singletonList(routingKey))
				.build());
		
		this.payloadType = payloadType;
		
		if (handleMethod != null) {
			Parameter[] parameters = handleMethod.getParameters();
			if (parameters.length == 1) {
				this.payloadType = parameters[0].getType();
			}
			this.setMethod(handleMethod.getName());
		}
	}
	
}
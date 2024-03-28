package com.zoomout.discovery.consumes;

import com.zoomout.model.ConsumeApi;
import com.zoomout.model.ConsumeApiType;
import com.zoomout.model.SpecType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Discover Consumes API AsyncApi - Rabbit MQ - RabbitTemplate
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(RabbitTemplate.class)
public class RabbitTemplateConsumesApisDiscovery implements ZoomOutConsumeDiscovery {
	
	private final Map<String, RabbitTemplate> rabbitTemplateMap;
	
	
	@Override
	public List<ConsumeApi> discovery() {
		Map<String, List<String>> consumeApiMap = new HashMap<>();
		
		rabbitTemplateMap.forEach((s, rabbitTemplate) -> {
			String exchange = rabbitTemplate.getExchange();
			String routingKey = rabbitTemplate.getRoutingKey();
			if (!consumeApiMap.containsKey(exchange)) {
				consumeApiMap.put(exchange, new ArrayList<>());
			}
			consumeApiMap.get(exchange).add(routingKey);
		});
		
		return consumeApiMap.entrySet().stream()
				.map(entry -> ConsumeApi.builder()
						.name(entry.getKey())
						.type(SpecType.ASYNC_API)
						.apiType(ConsumeApiType.RABBIT_MQ)
						.usedApis(entry.getValue()).build())
				.collect(Collectors.toList());
	}
}

package com.zoomout.discovery.consumes;

import com.zoomout.model.ConsumeApi;
import com.zoomout.model.ConsumeApiType;
import com.zoomout.model.SpecType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Discover Consume API OpenApi - RestTemplate
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(RestTemplate.class)
public class RestTemplateConsumesApisDiscovery implements ZoomOutConsumeDiscovery {
	
	private final Map<String, RequestEntity<?>> requestEntityMap;
	
	@Override
	public List<ConsumeApi> discovery() {
		Map<String, List<String>> consumeApiMap = new HashMap<>();
		
		requestEntityMap.forEach((beanName, requestEntity) -> {
			String consumeApiName = requestEntity.getUrl().getHost();
			if (!consumeApiMap.containsKey(consumeApiName)) {
				consumeApiMap.put(consumeApiName, new ArrayList<>());
			}
			String usedApi = String.format("%s - %s", requestEntity.getMethod(), requestEntity.getUrl());
			consumeApiMap.get(consumeApiName).add(usedApi);
		});
		
		return consumeApiMap.entrySet().stream()
				.map(entry -> ConsumeApi.builder()
						.name(entry.getKey())
						.type(SpecType.OPEN_API)
						.apiType(ConsumeApiType.REST)
						.usedApis(entry.getValue()).build())
				.collect(Collectors.toList());
	}
}

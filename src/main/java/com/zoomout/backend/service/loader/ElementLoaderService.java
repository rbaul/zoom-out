package com.zoomout.backend.service.loader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zoomout.backend.domain.model.Component;
import com.zoomout.backend.domain.model.InputApi;
import com.zoomout.backend.domain.model.OutputApi;
import com.zoomout.backend.domain.model.Resource;
import com.zoomout.backend.exception.NotFoundException;
import com.zoomout.backend.util.ObjectMapperUtils;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.Metadata;
import com.zoomout.model.SpecElement;
import com.zoomout.model.database.DatabaseDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ElementLoaderService {
	
	private final ModelMapper modelMapper;
	
	/**
	 * Load elements of one application
	 */
	public Component convert(List<Element> elements) {
		Element componentElement = elements.stream()
				.filter(element -> element.getKind() == KindType.COMPONENT)
				.findFirst().orElseThrow(() -> new NotFoundException("Not contain any component element"));
		
		Component component = new Component();
		Metadata metadata = componentElement.getMetadata();
		component.setName(metadata.getName());
		component.setDescription(metadata.getDescription());
		component.setTags(new HashSet<>(metadata.getTags()));
		component.setMetadata(metadata.getAdditional());
		
		
		SpecElement componentElementSpec = componentElement.getSpec();
		component.setDefinition(componentElementSpec.getDefinition());
		component.setOwners(new HashSet<>(Collections.singletonList(componentElementSpec.getOwner())));
		
		// dependsOn (DB)
		component.setResources(elements.stream()
				.filter(element -> element.getKind() == KindType.RESOURCE)
				.map(element -> {
					try {
						DatabaseDefinition databaseDefinition = ObjectMapperUtils.JSON.readValue(element.getSpec().getDefinition(), DatabaseDefinition.class);
						return modelMapper.map(databaseDefinition, Resource.class);
					} catch (JsonProcessingException e) {
						log.error("Failed parse", e);
						return null;
					}
					
				}).collect(Collectors.toList()));
		
		// Element with FLow
//		elements.stream()
//				.filter(element -> element.getKind() == KindType.FLOW)
//				.map(element -> {
//					BeansEndpoint.ApplicationBeans flow = ObjectMapperUtils.JSON.convertValue(element.getSpec().getDefinition(), BeansEndpoint.ApplicationBeans.class);
//					return modelMapper.map(flow, Resource.class);
//				}).collect(Collectors.toList()));
		
		// Element API Input
		component.setInputApis(elements.stream()
				.filter(element -> element.getKind() == KindType.API)
				.map(element -> {
					
					InputApi inputApi = new InputApi();
					inputApi.setName(element.getMetadata().getName());
					inputApi.setType(element.getSpec().getType().getValue());
					inputApi.setDefinition(element.getSpec().getDefinition());
					return inputApi;
				}).collect(Collectors.toList()));
		
		// Element API Output
		component.setOutputApis(componentElementSpec.getConsumesApis().stream().map(consumeApi -> {
			OutputApi outputApi = new OutputApi();
			outputApi.setName(consumeApi.getName());
			outputApi.setType(consumeApi.getApiType().name());
			outputApi.setApis(consumeApi.getUsedApis());
			return outputApi;
		}).collect(Collectors.toList()));
		
		return component;
	}
	
}

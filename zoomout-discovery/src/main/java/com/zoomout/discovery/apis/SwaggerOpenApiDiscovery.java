package com.zoomout.discovery.apis;

import com.zoomout.discovery.ZoomOutDiscovery;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import io.swagger.models.Swagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Discover API OpenApi
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(ServiceModelToSwagger2Mapper.class)
@ConditionalOnBean(ServiceModelToSwagger2Mapper.class)
public class SwaggerOpenApiDiscovery implements ZoomOutDiscovery {
	
	private final ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper;
	
	private final JsonSerializer jsonSerializer;
	
	private final DocumentationCache documentationCache;
	
	@Override
	public KindType getType() {
		return KindType.API;
	}
	
	@Override
	public SpecType getSpecType() {
		return SpecType.OPEN_API;
	}
	
	@Override
	public List<Element> discovery() {
		List<Element> apis = new ArrayList<>();
		// Discover swagger
		Swagger swaggerDoc = serviceModelToSwagger2Mapper.mapDocumentation(documentationCache.documentationByGroup("default"));
		Json aDefault = jsonSerializer.toJson(swaggerDoc);
		
		apis.add(Element.builder()
				.apiVersion(Element.BACKSTAGE_IO_V_1_ALPHA_1)
				.kind(KindType.API)
				.spec(SpecElement.builder()
						.type(SpecType.OPEN_API)
						.definition(aDefault.value()).build()).build());

//		applicationContext.getBean(JsonSerializer.class).toJson(applicationContext.getBean(ServiceModelToSwagger2Mapper.class).mapDocumentation(applicationContext.getBean(DocumentationCache.class).documentationByGroup("default")))
		
		// Discover swagger openapi
		// Discover opensync RabbitMQ/Kafka
		return apis;
	}
}

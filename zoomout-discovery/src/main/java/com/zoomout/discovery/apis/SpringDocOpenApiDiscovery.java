package com.zoomout.discovery.apis;

import com.zoomout.discovery.ZoomOutDiscovery;
import com.zoomout.discovery.utils.AppMetadataHolder;
import com.zoomout.discovery.utils.FakeHttpServletRequest;
import com.zoomout.discovery.utils.ObjectMapperUtils;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.Metadata;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import com.zoomout.model.api.ApiDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.Constants;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springdoc.webmvc.core.SpringWebMvcProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Discover API OpenApi
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(OpenApiWebMvcResource.class)
//@ConditionalOnBean(OpenApiWebMvcResource.class)
public class SpringDocOpenApiDiscovery implements ZoomOutDiscovery {
	
	private final OpenApiWebMvcResource openApiWebMvcResource;
	
	private final SpringWebMvcProvider springWebMvcProvider;
	
	private final AppMetadataHolder appMetadataHolder;
	
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
		Map<?, ?> handlerMethods = springWebMvcProvider.getHandlerMethods();
		Map<String, ApiDefinition> apiDefinitionMap = new HashMap<>();
		handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
			String requestMappingInfoString = ((RequestMappingInfo) requestMappingInfo).toString();
			String handlerMethodString = ((HandlerMethod) handlerMethod).toString();
			apiDefinitionMap.put(requestMappingInfoString, ApiDefinition.builder()
					.url(requestMappingInfoString)
					.method(handlerMethodString).build());
		});
		
		SpecElement specElement = SpecElement.builder()
				.type(SpecType.OPEN_API).build();
		Element openApiElement = Element.builder()
				.kind(KindType.API)
				.metadata(Metadata.builder()
						.name(getApiName())
						.additional(Map.of("api", ObjectMapperUtils.valueToTree(apiDefinitionMap))).build())
				.spec(specElement).build();
		
		try {
			HttpServletRequest httpServletRequest = new FakeHttpServletRequest();
			String openapiYaml = openApiWebMvcResource.openapiYaml(httpServletRequest, Constants.DEFAULT_API_DOCS_URL, Locale.ENGLISH);
			specElement.setDefinition(openapiYaml);
		} catch (Exception e) {
			log.error("Failed read api-docs", e);
		}
		
		apis.add(openApiElement);

//		applicationContext.getBean(JsonSerializer.class).toJson(applicationContext.getBean(ServiceModelToSwagger2Mapper.class).mapDocumentation(applicationContext.getBean(DocumentationCache.class).documentationByGroup("default")))
		
		return apis;
	}
	
	private String getApiName() {
		return String.format("%s-%s", appMetadataHolder.getApplicationName(), getSpecType().getValue());
	}
	
}

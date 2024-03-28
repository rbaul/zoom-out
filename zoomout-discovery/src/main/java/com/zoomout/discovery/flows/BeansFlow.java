package com.zoomout.discovery.flows;

import com.zoomout.discovery.ZoomOutDiscovery;
import com.zoomout.discovery.utils.ObjectMapperUtils;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnClass(BeansEndpoint.class)
@RequiredArgsConstructor
public class BeansFlow implements ZoomOutDiscovery {
	
	private final BeansEndpoint beansEndpoint;
	
	private final ApplicationContext applicationContext;
	
	public Element getFlows() {
		
		final String mainPackage = getMainPackageName();
		
		SpecElement specElement = SpecElement.builder().build();
		Element flowElement = Element.builder()
				.kind(KindType.FLOW)
				.spec(specElement).build();
		
		BeansEndpoint.ApplicationBeans contexts = beansEndpoint.beans();
		
		contexts.getContexts().forEach((contextName, contextBeans) -> {
			Map<String, BeansEndpoint.BeanDescriptor> filteredBeans = contextBeans.getBeans().entrySet().stream()
					.filter(entry -> entry.getValue().getType().getPackageName().startsWith(mainPackage))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			
			contextBeans.getBeans().clear();
			contextBeans.getBeans().putAll(filteredBeans);
		});
		
		specElement.setDefinition(ObjectMapperUtils.toString(contexts));
		
		return flowElement;
	}
	
	/**
	 * Get Main Package of application
	 */
	private String getMainPackageName() {
		Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
		if (!CollectionUtils.isEmpty(beansWithAnnotation)) {
			Object next = beansWithAnnotation.values().iterator().next();
			return next.getClass().getPackageName();
		}
		return "";
	}
	
	@Override
	public KindType getType() {
		return KindType.FLOW;
	}
	
	@Override
	public SpecType getSpecType() {
		return SpecType.FLOW;
	}
	
	@Override
	public List<Element> discovery() {
		return List.of(getFlows());
	}
}

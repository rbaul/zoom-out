package com.zoomout.discovery;

import com.zoomout.discovery.consumes.ZoomOutConsumeDiscovery;
import com.zoomout.discovery.uml.UmlClassDiscovery;
import com.zoomout.discovery.uml.model.Node;
import com.zoomout.discovery.utils.AppMetadataHolder;
import com.zoomout.discovery.utils.ObjectMapperUtils;
import com.zoomout.model.ConsumeApi;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class ZoomOutDiscoveryService {
	
	private final Environment env;
	
	private final AppMetadataHolder appMetadataHolder;

//	private final StringValueResolver resolver;
	
	private final ApplicationContext applicationContext;
	
	private List<Element> cache;
	
	private final List<ZoomOutDiscovery> discoveries;
	
	private final List<ZoomOutConsumeDiscovery> consumeDiscoveries;
	
	/**
	 * Discovery application if not cached
	 */
	public List<Element> discover() {
		if (CollectionUtils.isEmpty(cache)) {
			cache = discoverAll();
		}
		return cache;
	}
	
	/**
	 * Discovery application
	 */
	public List<Element> discoverAll() {
		SpecElement specElement = SpecElement.builder()
				.type(SpecType.SERVICE)
				.dependsOn(new ArrayList<>())
				.providesApis(new ArrayList<>())
				.consumesApis(new ArrayList<>())
				.build();
		Element serviceElement = Element.builder()
				.kind(KindType.COMPONENT)
				.metadata(appMetadataHolder.build())
				.spec(specElement).build();
		
		List<Element> elements = new ArrayList<>();
		
		discoveries.forEach(zoomOutDiscovery -> elements.addAll(zoomOutDiscovery.discovery()));
		
		elements.forEach(element -> {
			if (element.getKind() == KindType.API) {
				specElement.getProvidesApis().add(element.getMetadata().getName());
			} else if (element.getKind() == KindType.RESOURCE) {
				specElement.getDependsOn().add(element.getMetadata().getName());
			}
		});
		
		consumeDiscoveries.forEach(zoomOutConsumeDiscovery -> {
			List<ConsumeApi> discovery = zoomOutConsumeDiscovery.discovery();
			if (!CollectionUtils.isEmpty(discovery)) {
				discovery.forEach(consumeApi -> specElement.getConsumesApis().add(consumeApi));
			}
		});
		
		// Service definition: UML
		List<Node> umlDefinition = UmlClassDiscovery.getUmlByPackage(appMetadataHolder.getAppMainPackage());
		specElement.setDefinition(ObjectMapperUtils.toString(umlDefinition));
		
		elements.add(serviceElement);
		
		return elements;
	}
	
}

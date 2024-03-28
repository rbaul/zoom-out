package com.zoomout.discovery.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.zoomout.model.Metadata;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@Getter
public class AppMetadataHolder {
	
	@Value("${spring.application.name:default}")
	private String applicationName;
	
	private String javaBuildVersion;
	
	private String appMainPackage;

//	private final BuildProperties buildProperties;
	
	public AppMetadataHolder(ApplicationContext applicationContext) {
		applicationContext.getBeansWithAnnotation(SpringBootApplication.class).entrySet().stream()
				.findFirst()
				.ifPresent(entry -> {
					Package appPackage = entry.getValue().getClass().getPackage();
					javaBuildVersion = appPackage.getImplementationVersion();
					appMainPackage = appPackage.getName();
				});
	}
	
	public Metadata build() {
		Map<String, JsonNode> additional = new HashMap<>();
		additional.put("java", new TextNode(Runtime.version().toString()));
		additional.put("spring-core", new TextNode(SpringVersion.getVersion()));
		additional.put("spring-boot", new TextNode(SpringBootVersion.getVersion()));
		additional.put("package", new TextNode(appMainPackage));
		
		return Metadata.builder()
				.name(applicationName)
				.additional(additional)
				.description(getDescription())
				.tags(getTags()).build();
	}
	
	private String getDescription() {
		return null;
	}
	
	private List<String> getTags() {
		return List.of("java", "spring");
	}
	
}
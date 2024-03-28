package com.zoomout.backend.web.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ComponentDto {
	
	/**
	 * Component ID
	 */
	private String id;
	
	/**
	 * Component Name
	 */
	private String name;
	
	/**
	 * Component description
	 */
	private String description;
	
	/**
	 * Application url
	 */
	private String url;
	
	/**
	 * Component owner
	 */
	private Set<String> owners;
	
	/**
	 * Component tags
	 */
	private Set<String> tags;
	
	/**
	 * Metadata
	 */
	private Map<String, JsonNode> metadata;
	
}

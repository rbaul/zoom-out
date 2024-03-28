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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApplicationDto {
	
	/**
	 * Application ID
	 */
	private String id;
	
	/**
	 * Application name
	 */
	private String name;
	
	/**
	 * Application description
	 */
	private String description;
	
	/**
	 * Components info
	 */
	private List<ComponentInfoDto> components;
	
	/**
	 * Links info
	 */
	private List<LinkInfoDto> links;
	
	/**
	 * Groups info
	 */
	private List<GroupDto> groups;
	
	/**
	 * Metadata
	 */
	private Map<String, JsonNode> metadata;
	
}

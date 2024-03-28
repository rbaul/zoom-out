package com.zoomout.backend.web.dto;

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
public class ComponentInfoDto {
	
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
	 * Application url
	 */
	private String url;
	
	/**
	 * Application owner
	 */
	private List<String> owners;
	
	/**
	 * Application tags
	 */
	private List<String> tags;
	
	/**
	 * Metadata
	 */
	private Map<String, String> metadata;
	
}

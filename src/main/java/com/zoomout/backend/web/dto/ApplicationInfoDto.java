package com.zoomout.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApplicationInfoDto {
	
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
	
}

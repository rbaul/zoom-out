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
public class InputApiDto {
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * name
	 */
	private String name;
	
	/**
	 * type
	 */
	private String type;
	
	/**
	 * Definition
	 */
	private String definition;
	
}

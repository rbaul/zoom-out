package com.zoomout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumeApi {
	/**
	 * API name
	 */
	private String name;
	
	private SpecType type;
	
	private ConsumeApiType apiType;
	
	private List<String> usedApis;
}

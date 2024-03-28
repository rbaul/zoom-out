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
public class SpecElement {
	private SpecType type;
	
	private LifecycleType lifecycle;
	
	private String owner;
	
	private List<String> dependsOn;
	
	/**
	 * Inputs
	 */
	private List<String> providesApis;
	
	/**
	 * Outputs
	 */
	private List<ConsumeApi> consumesApis;
	
	private String definition;
	
	private Profile profile;
	
	private String parent;
	
	private List<String> children;
	
}

package com.zoomout.discovery.uml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Annotation {
	/**
	 * Name of Annotation
	 */
	private String name;
	
	/**
	 * Package of annotation class
	 */
	private String packageName;
}
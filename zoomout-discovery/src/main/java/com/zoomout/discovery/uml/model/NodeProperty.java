package com.zoomout.discovery.uml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NodeProperty {
	/**
	 * Field name
	 */
	private String name;
	
	/**
	 * Type of field
	 */
	private String type;
	
	/**
	 * Access modifier
	 */
	private AccessModifier accessModifier;
	
	/**
	 * Modifier restricting to one instance
	 */
	@Builder.Default
	private Boolean staticModifier = false;
	
	/**
	 * Modifier prohibiting value modification
	 */
	@Builder.Default
	private Boolean finalModifier = false;
	
	/**
	 * Annotations
	 */
	@Singular
	private List<Annotation> annotations;
}

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
public class NodeMethod {
	public static final String VOID = "void";
	
	/**
	 * Name
	 */
	private String name;
	
	/**
	 * Return type
	 */
	@Builder.Default()
	private String returnType = VOID;
	
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
	 * Modifier requiring override
	 */
	@Builder.Default
	private Boolean abstractModifier = false;
	
	/**
	 * Modifier preventing reentrancy
	 */
	@Builder.Default
	private Boolean synchronizedModifier = false;
	
	/**
	 * Arguments
	 */
	@Singular
	private List<NodeProperty> parameters;
	
	/**
	 * Annotations
	 */
	@Singular
	private List<Annotation> annotations;
}

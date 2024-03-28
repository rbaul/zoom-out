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
public class Node {
	
	/**
	 * Class name
	 */
	private String name;
	
	/**
	 * Class package
	 */
	private String packageName;
	
	/**
	 * Type of class
	 */
	private NodeType type;
	
	/**
	 * Class modifiers
	 */
	private AccessModifier accessModifier;
	
	/**
	 * Modifier restricting to one instance
	 */
	private Boolean staticModifier = false;
	
	/**
	 * Modifier prohibiting value modification
	 */
	private Boolean finalModifier = false;
	
	/**
	 * Modifier requiring override
	 */
	@Builder.Default
	private Boolean abstractModifier = false;
	
	/**
	 * Annotations
	 */
	@Singular
	private List<Annotation> annotations;
	
	/**
	 * Constants
	 */
	@Singular
	private List<NodeProperty> constants;
	
	/**
	 * Properties - Fields
	 */
	@Singular
	private List<NodeProperty> properties;
	
	/**
	 * Constructors
	 */
	@Singular
	private List<NodeMethod> constructors;
	
	/**
	 * Methods
	 */
	@Singular
	private List<NodeMethod> methods;
	
	
}

package com.zoomout.discovery.uml;

import com.zoomout.discovery.uml.model.AccessModifier;
import com.zoomout.discovery.uml.model.Annotation;
import com.zoomout.discovery.uml.model.Node;
import com.zoomout.discovery.uml.model.NodeMethod;
import com.zoomout.discovery.uml.model.NodeProperty;
import com.zoomout.discovery.uml.model.NodeType;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UmlClassDiscovery {
	
	private static final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
	
	static {
		// add include filters which matches all the classes (or use your own)
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
	}
	
	/**
	 * Build UML classes by package
	 */
	public static List<Node> getUmlByPackage(String packageName) {
		List<Node> nodeList = new ArrayList<>();
		List<? extends Class<?>> allClassesByPackage = getAllClassesByPackage(packageName);
		
		allClassesByPackage.forEach(aClass -> {
			// Relationship
			Class<?>[] interfaces = aClass.getInterfaces();
			Class<?> superclass = aClass.getSuperclass();
			
			Node node = Node.builder()
					.name(aClass.getSimpleName())
					.packageName(aClass.getPackageName())
					.type(getNodeTypeByClass(aClass))
					.accessModifier(getAccessModifier(aClass.getModifiers()))
					.finalModifier(isFinalModifier(aClass.getModifiers()))
					.staticModifier(isStaticModifier(aClass.getModifiers()))
					.abstractModifier(isAbstractModifier(aClass.getModifiers()))
					.annotations(convertAnnotations(aClass.getDeclaredAnnotations()))
					.properties(convertFieldsToNodeProperties(aClass.getDeclaredFields()))
					.build();
			
			
			// Methods
			Method[] declaredMethods = aClass.getDeclaredMethods();
			List<NodeMethod> nodeMethods = Arrays.stream(declaredMethods).map(method -> NodeMethod.builder()
					.name(method.getName())
					.returnType(method.getReturnType().getName())
					.accessModifier(getAccessModifier(method.getModifiers()))
					.finalModifier(isFinalModifier(method.getModifiers()))
					.staticModifier(isStaticModifier(method.getModifiers()))
					.abstractModifier(isAbstractModifier(method.getModifiers()))
					.synchronizedModifier(isSynchronizedModifier(method.getModifiers()))
					.annotations(convertAnnotations(method.getDeclaredAnnotations()))
					.parameters(convertParametersToNodeProperties(method.getParameters()))
					.build()).collect(Collectors.toList());
			node.setMethods(nodeMethods);
			
			// Constructors
			Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
			List<NodeMethod> nodeConstructors = Arrays.stream(declaredConstructors).map(constructor -> NodeMethod.builder()
					.name(constructor.getName())
					.returnType(NodeMethod.VOID)
					.accessModifier(getAccessModifier(constructor.getModifiers()))
					.finalModifier(isFinalModifier(constructor.getModifiers()))
					.staticModifier(isStaticModifier(constructor.getModifiers()))
					.annotations(convertAnnotations(constructor.getDeclaredAnnotations()))
					.parameters(convertParametersToNodeProperties(constructor.getParameters()))
					.build()).collect(Collectors.toList());
			node.setConstructors(nodeConstructors);
			
			// Enums
			if (aClass.isEnum()) {
				Object[] enumConstants = aClass.getEnumConstants();
			}
			
			nodeList.add(node);
		});
		
		return nodeList;
	}
	
	private static List<NodeProperty> convertFieldsToNodeProperties(Field[] declaredFields) {
		return Arrays.stream(declaredFields).map(UmlClassDiscovery::convertFieldToNodeProperty).collect(Collectors.toList());
	}
	
	private static NodeProperty convertFieldToNodeProperty(Field field) {
		return NodeProperty.builder()
				.name(field.getName())
				.type(field.getType().getName())
				.accessModifier(getAccessModifier(field.getModifiers()))
				.finalModifier(isFinalModifier(field.getModifiers()))
				.staticModifier(isStaticModifier(field.getModifiers()))
				.annotations(convertAnnotations(field.getDeclaredAnnotations()))
				.build();
	}
	
	private static List<NodeProperty> convertParametersToNodeProperties(Parameter[] parameters) {
		return Arrays.stream(parameters)
				.map(UmlClassDiscovery::convertParameterToNodeProperty)
				.collect(Collectors.toList());
	}
	
	private static NodeProperty convertParameterToNodeProperty(Parameter parameter) {
		return NodeProperty.builder()
				.name(parameter.getName())
				.type(parameter.getType().getSimpleName())
				.annotations(convertAnnotations(parameter.getDeclaredAnnotations()))
				.build();
	}
	
	private static List<Annotation> convertAnnotations(java.lang.annotation.Annotation[] annotations) {
		return Arrays.stream(annotations)
				.map(UmlClassDiscovery::convertToAnnotation)
				.collect(Collectors.toList());
	}
	
	private static Annotation convertToAnnotation(java.lang.annotation.Annotation annotation) {
		return Annotation.builder()
				.name(annotation.annotationType().getSimpleName())
				.packageName(annotation.annotationType().getPackageName()).build();
	}
	
	/**
	 * Get Type by class
	 */
	private static NodeType getNodeTypeByClass(Class<?> aClass) {
		if (aClass.isInterface()) {
			return NodeType.INTERFACE;
		} else if (aClass.isEnum()) {
			return NodeType.ENUM;
		} else {
			return NodeType.CLASS;
		}
	}
	
	/**
	 * Convert Access modifier
	 */
	private static AccessModifier getAccessModifier(int modifier) {
		String modifierString = Modifier.toString(modifier);
		if (modifierString.contains("public")) {
			return AccessModifier.PUBLIC;
		} else if (modifierString.contains("protected")) {
			return AccessModifier.PROTECTED;
		} else if (modifierString.contains("private")) {
			return AccessModifier.PRIVATE;
		} else {
			return null;
		}
	}
	
	/**
	 * is Static modifier
	 */
	private static Boolean isStaticModifier(int modifier) {
		String modifierString = Modifier.toString(modifier);
		return modifierString.contains("static");
	}
	
	/**
	 * is final modifier
	 */
	private static Boolean isFinalModifier(int modifier) {
		String modifierString = Modifier.toString(modifier);
		return modifierString.contains("final");
	}
	
	/**
	 * is abstract modifier
	 */
	private static Boolean isAbstractModifier(int modifier) {
		String modifierString = Modifier.toString(modifier);
		return modifierString.contains("abstract");
	}
	
	/**
	 * is synchronized modifier
	 */
	private static Boolean isSynchronizedModifier(int modifier) {
		String modifierString = Modifier.toString(modifier);
		return modifierString.contains("synchronized");
	}
	
	public static List<? extends Class<?>> getAllClassesByPackage(String packagePath) {
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				AnnotationMetadata metadata = beanDefinition.getMetadata();
				return (metadata.isIndependent() && (metadata.isConcrete() ||
						(metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName())) || metadata.isInterface()));
			}
		};
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
		List<? extends Class<?>> collect = provider.findCandidateComponents(packagePath).stream().map(beanDefinition -> {
			String beanClassName = beanDefinition.getBeanClassName();
			try {
				return Class.forName(beanClassName);
			} catch (ClassNotFoundException e) {
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.toList());
		return collect;
		
	}
	
}

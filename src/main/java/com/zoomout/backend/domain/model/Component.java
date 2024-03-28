package com.zoomout.backend.domain.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.util.CollectionUtils;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "component_unique", columnNames = {"application_id", "name"}))
public class Component {
	public static final String COMPONENT = "component";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	/**
	 * Definition of component (UML)
	 */
	@Column(columnDefinition = "longtext")
	private String definition;
	
	/**
	 * Service tags
	 */
	@ElementCollection
	private Set<String> tags = new HashSet<>();
	
	/**
	 * Service owners
	 */
	@ElementCollection
	private Set<String> owners = new HashSet<>();
	
	
	/**
	 * Bean dependencies
	 */
	private String flow;
	
	/**
	 * Input APIs of component, like REST, ASYNC, GRPC
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = COMPONENT)
	private List<InputApi> inputApis = new ArrayList<>();
	
	/**
	 * Input APIs of component, like REST, ASYNC, GRPC
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = COMPONENT)
	private List<OutputApi> outputApis = new ArrayList<>();
	
	/**
	 * Resources of component, like DB
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = COMPONENT)
	private List<Resource> resources = new ArrayList<>();
	
	@Version
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private Short version;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Application application;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Group group;
	
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	@Column(columnDefinition = "longtext")
	private Map<String, JsonNode> metadata = new HashMap<>();
	
	public void setInputApis(List<InputApi> inputApis) {
		if (!CollectionUtils.isEmpty(this.inputApis)) {
			this.inputApis.forEach(this::removeInputApi);
		}
		
		if (!CollectionUtils.isEmpty(inputApis)) {
			inputApis.forEach(this::addInputApi);
		}
	}
	
	public void addInputApi(InputApi inputApi) {
		if (inputApis == null) {
			inputApis = new ArrayList<>();
		}
		inputApis.add(inputApi);
		inputApi.setComponent(this);
	}
	
	public void removeInputApi(InputApi inputApi) {
		if (!CollectionUtils.isEmpty(inputApis)) {
			inputApis.remove(inputApi);
			inputApi.setComponent(null);
		}
	}
	
	public void setOutputApis(List<OutputApi> outputApis) {
		if (!CollectionUtils.isEmpty(this.outputApis)) {
			this.outputApis.forEach(this::removeOutputApi);
		}
		
		if (!CollectionUtils.isEmpty(outputApis)) {
			outputApis.forEach(this::addOutputApi);
		}
	}
	
	public void addOutputApi(OutputApi outputApi) {
		if (outputApis == null) {
			outputApis = new ArrayList<>();
		}
		outputApis.add(outputApi);
		outputApi.setComponent(this);
	}
	
	public void removeOutputApi(OutputApi outputApi) {
		if (!CollectionUtils.isEmpty(outputApis)) {
			outputApis.remove(outputApi);
			outputApi.setComponent(null);
		}
	}
	
	public void setResources(List<Resource> resources) {
		if (!CollectionUtils.isEmpty(this.resources)) {
			this.resources.forEach(this::removeResource);
		}
		
		if (!CollectionUtils.isEmpty(resources)) {
			resources.forEach(this::addResource);
		}
	}
	
	public void addResource(Resource resource) {
		if (resources == null) {
			resources = new ArrayList<>();
		}
		resources.add(resource);
		resource.setComponent(this);
	}
	
	public void removeResource(Resource resource) {
		if (!CollectionUtils.isEmpty(resources)) {
			resources.remove(resource);
			resource.setComponent(null);
		}
	}
	
}

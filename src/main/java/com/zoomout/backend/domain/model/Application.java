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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application representation
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Application {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	@Column(unique = true)
	private String name;
	
	private String description;
	
	@Version
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private Short version;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private List<Component> components = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private List<Link> links = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "application")
	private List<Group> groups = new ArrayList<>();
	
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	@Column(columnDefinition = "longtext")
	private Map<String, JsonNode> metadata = new HashMap<>();
	
	public void setComponents(List<Component> components) {
		if (!CollectionUtils.isEmpty(this.components)) {
			this.components.forEach(this::removeComponent);
		}
		
		if (!CollectionUtils.isEmpty(components)) {
			components.forEach(this::addComponent);
		}
	}
	
	public void addComponent(Component component) {
		if (components == null) {
			components = new ArrayList<>();
		}
		components.add(component);
		component.setApplication(this);
	}
	
	public void removeComponent(Component component) {
		if (!CollectionUtils.isEmpty(components)) {
			components.remove(component);
			component.setApplication(null);
		}
	}
	
	public void setLinks(List<Link> links) {
		if (!CollectionUtils.isEmpty(this.links)) {
			this.links.forEach(this::removeLink);
		}
		
		if (!CollectionUtils.isEmpty(links)) {
			links.forEach(this::addLink);
		}
	}
	
	public void addLink(Link link) {
		if (links == null) {
			links = new ArrayList<>();
		}
		links.add(link);
		link.setApplication(this);
	}
	
	public void removeLink(Link link) {
		if (!CollectionUtils.isEmpty(links)) {
			links.remove(link);
			link.setApplication(null);
		}
	}
	
	public void setGroups(List<Group> groups) {
		if (!CollectionUtils.isEmpty(this.groups)) {
			this.groups.forEach(this::removeGroup);
		}
		
		if (!CollectionUtils.isEmpty(groups)) {
			groups.forEach(this::addGroup);
		}
	}
	
	public void addGroup(Group group) {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		groups.add(group);
		group.setApplication(this);
	}
	
	public void removeGroup(Group group) {
		if (!CollectionUtils.isEmpty(groups)) {
			groups.remove(group);
			group.setApplication(null);
		}
	}
}

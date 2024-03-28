package com.zoomout.backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "group_doc")
public class Group {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "group")
	private List<Component> components = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "group")
	private List<Group> groups = new ArrayList<>();
	
	@Version
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private Short version;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Group group;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Application application;
	
	
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
		component.setGroup(this);
	}

	public void removeComponent(Component component) {
		if (!CollectionUtils.isEmpty(components)) {
			components.remove(component);
			component.setGroup(null);
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
		group.setGroup(this);
	}
	
	public void removeGroup(Group group) {
		if (!CollectionUtils.isEmpty(groups)) {
			groups.remove(group);
			group.setGroup(null);
		}
	}
}

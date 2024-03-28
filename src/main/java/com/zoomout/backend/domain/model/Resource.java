package com.zoomout.backend.domain.model;

import com.zoomout.backend.domain.model.resource.EntityDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Resource {
	@Id
	@GeneratedValue
	private Long id;
	
//	@NotEmpty
	private String name;
	
	@NotEmpty
	private String type;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Map<String, EntityDefinition> entities;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Component component;
	
}

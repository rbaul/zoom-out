package com.zoomout.backend.domain.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class EntityDefinition {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String repository;
	
	@ElementCollection
	private Map<String, FieldDefinition> fields = new HashMap<>();
}

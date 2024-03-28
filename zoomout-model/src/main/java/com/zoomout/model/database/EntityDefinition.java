package com.zoomout.model.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityDefinition {
	private String name;
	
	private String repository;
	
	private Map<String, FieldDefinition> fields = new HashMap<>();
}

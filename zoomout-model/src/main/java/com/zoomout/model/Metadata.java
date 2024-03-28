package com.zoomout.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Metadata {
	private String name;
	
	private String description;
	
	private List<String> tags;
	
	private List<Link> links;
	
	private Map<String, JsonNode> additional;
}

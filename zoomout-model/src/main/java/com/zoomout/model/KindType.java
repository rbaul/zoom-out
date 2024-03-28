package com.zoomout.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KindType {
	COMPONENT("Component"),
	API("API"),
	GROUP("Group"),
	RESOURCE("Resource"),
	
	// Additional
	FLOW("Flow");
	
	@Getter(onMethod_ = @JsonValue)
	private final String value;
	
}

package com.zoomout.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SpecType {
	SERVICE("service"),
	OPEN_API("openapi"),
	GRPC("grpc"),
	ASYNC_API("asyncapi"),
	GRAPHQL("graphql"),
	TEAM("team"),
	DATABASE("database"),
	
	// Additional
	FLOW("flow");
	
	@Getter(onMethod_ = @JsonValue)
	private final String value;
	
}

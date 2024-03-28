package com.zoomout.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LifecycleType {
	PRODUCTION("production");
	
	@Getter(onMethod_ = @JsonValue)
	private final String value;
	
}

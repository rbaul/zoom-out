package com.zoomout.discovery.consumes;

import com.zoomout.model.ConsumeApi;

import java.util.List;

public interface ZoomOutConsumeDiscovery {
	
	default boolean isRelevant() {
		return true;
	}
	
	List<ConsumeApi> discovery();
	
}

package com.zoomout.discovery;

import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.SpecType;

import java.util.List;

public interface ZoomOutDiscovery {
	
	KindType getType();
	
	SpecType getSpecType();
	
	default boolean isRelevant() {
		return true;
	}
	
	List<Element> discovery();
	
}

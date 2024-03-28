package com.zoomout.backend.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@ConfigurationProperties("zoom-out.app-doc")
public class AppDocZoomOutProperties {
	
	/**
	 * Urls to Application documentation
	 */
	private List<String> urls = new ArrayList<>();
	
	/**
	 * Paths location to Application documentation
	 */
	private List<String> paths = new ArrayList<>();
}

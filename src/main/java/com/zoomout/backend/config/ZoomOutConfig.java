package com.zoomout.backend.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@EnableConfigurationProperties({
		AppDocZoomOutProperties.class
})
@Configuration
public class ZoomOutConfig {
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.build();
	}
}

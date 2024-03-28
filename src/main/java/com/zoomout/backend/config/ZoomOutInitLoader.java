package com.zoomout.backend.config;

import com.zoomout.backend.domain.model.Application;
import com.zoomout.backend.service.ApplicationDocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ZoomOutInitLoader {
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> initialLoader(ApplicationDocService applicationDocService, AppDocZoomOutProperties appDocZoomOutProperties) {
		return event -> {
			Application defaultApplicationDoc = applicationDocService.createDefaultApplicationDoc(appDocZoomOutProperties);
			applicationDocService.addApplicationDoc(defaultApplicationDoc);
		};
	}
}

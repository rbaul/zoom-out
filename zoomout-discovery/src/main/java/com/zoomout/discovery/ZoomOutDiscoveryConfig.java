package com.zoomout.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@Slf4j
@ComponentScan
public class ZoomOutDiscoveryConfig {
//	@Autowired
//	private ZoomOutDiscoveryService zoomOutDiscoveryService;
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void applicationReady() {
//		log.info("Start architecture discovery");
//		zoomOutDiscoveryService.discover();
//		log.info("End architecture discovery");
//	}
}

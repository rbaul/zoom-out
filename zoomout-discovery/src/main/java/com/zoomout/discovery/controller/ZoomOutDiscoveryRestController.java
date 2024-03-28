package com.zoomout.discovery.controller;

import com.zoomout.discovery.ZoomOutDiscoveryService;
import com.zoomout.model.Element;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/zoom-out")
public class ZoomOutDiscoveryRestController {
	
	private final ZoomOutDiscoveryService zoomOutDiscoveryService;
	
	/**
	 * Application doc json string.
	 */
	@GetMapping(value = "/app-doc", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Element> zoomOutApi() {
		return zoomOutDiscoveryService.discover();
	}
}

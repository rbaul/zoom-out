package com.zoomout.backend.web.controller;

import com.zoomout.backend.service.ComponentDocService;
import com.zoomout.backend.web.dto.ComponentDto;
import com.zoomout.backend.web.dto.InputApiDto;
import com.zoomout.backend.web.dto.OutputApiInfoDto;
import com.zoomout.backend.web.dto.ResourceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/components")
public class ComponentDocController {
	
	private final ComponentDocService componentDocService;
	
	/**
	 * Get Component
	 */
	@GetMapping("{id}")
	public ComponentDto getComponent(@PathVariable long id) {
		return componentDocService.getComponent(id);
	}
	
	/**
	 * Get Component resources
	 */
	@GetMapping("{id}/resources")
	public List<ResourceDto> getComponentResources(@PathVariable long id) {
		return componentDocService.getComponentResources(id);
	}
	
	/**
	 * Get Component definition
	 */
	@GetMapping("{id}/definition")
	public String getComponentDefinition(@PathVariable long id) {
		return componentDocService.getComponentDefinition(id);
	}
	
	/**
	 * Get Component Input Apis
	 */
	@GetMapping("{id}/input-apis")
	public List<InputApiDto> getComponentInputApis(@PathVariable long id) {
		return componentDocService.getComponentInputApis(id);
	}
	
	/**
	 * Get Component Output Apis
	 */
	@GetMapping("{id}/output-apis")
	public List<OutputApiInfoDto> getComponentOutputApis(@PathVariable long id) {
		return componentDocService.getComponentOutputApis(id);
	}
	
	/**
	 * Delete Component
	 */
	@DeleteMapping("{id}")
	public void deleteComponent(@PathVariable long id) {
		componentDocService.deleteComponent(id);
	}
	
}

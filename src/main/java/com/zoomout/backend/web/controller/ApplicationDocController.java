package com.zoomout.backend.web.controller;

import com.zoomout.backend.config.AppDocZoomOutProperties;
import com.zoomout.backend.service.ApplicationDocService;
import com.zoomout.backend.service.ComponentDocService;
import com.zoomout.backend.service.GroupDocService;
import com.zoomout.backend.service.LinkDocService;
import com.zoomout.backend.web.dto.ApplicationDto;
import com.zoomout.backend.web.dto.ApplicationInfoDto;
import com.zoomout.backend.web.dto.ComponentInfoDto;
import com.zoomout.backend.web.dto.GroupDto;
import com.zoomout.backend.web.dto.LinkInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/applications")
public class ApplicationDocController {
	
	private final ApplicationDocService applicationDocService;
	
	private final ComponentDocService componentDocService;
	
	private final GroupDocService groupDocService;
	
	private final LinkDocService linkDocService;
	
	/**
	 * Get all application Info
	 */
	@GetMapping
	public Page<ApplicationInfoDto> getApplicationsInformation(@PageableDefault Pageable pageable) {
		return applicationDocService.getApplicationsInformation(pageable);
	}
	
	/**
	 * Get Application by id
	 */
	@GetMapping("{id}")
	public ApplicationDto getApplication(@PathVariable long id) {
		return applicationDocService.getApplication(id);
	}
	
	/**
	 * Delete Application by id
	 */
	@DeleteMapping("{id}")
	public void deleteApplication(@PathVariable long id) {
		applicationDocService.deleteApplication(id);
	}
	
	/**
	 * Get components of application
	 */
	@GetMapping("{id}/components")
	public Page<ComponentInfoDto> getComponents(@PathVariable long id, @PageableDefault Pageable pageable) {
		return componentDocService.getComponentsByApplicationId(id, pageable);
	}
	
	/**
	 * Get groups of application
	 */
	@GetMapping("{id}/groups")
	public Page<GroupDto> getGroups(@PathVariable long id, @PageableDefault Pageable pageable) {
		return groupDocService.getGroupsByApplicationId(id, pageable);
	}
	
	/**
	 * Get links of application
	 */
	@GetMapping("{id}/links")
	public Page<LinkInfoDto> getLinks(@PathVariable long id, @PageableDefault Pageable pageable) {
		return linkDocService.getLinksByApplicationId(id, pageable);
	}
	
	/**
	 * Add components to application
	 */
	@PostMapping("{id}/components")
	public void addComponents(@PathVariable long id, @RequestBody AppDocZoomOutProperties appDocZoomOutProperties) {
		applicationDocService.addComponents(id, appDocZoomOutProperties);
	}
	
}

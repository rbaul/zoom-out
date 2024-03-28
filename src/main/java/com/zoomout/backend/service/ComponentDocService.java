package com.zoomout.backend.service;

import com.zoomout.backend.domain.model.Component;
import com.zoomout.backend.domain.repository.ComponentRepository;
import com.zoomout.backend.domain.repository.InputApiRepository;
import com.zoomout.backend.domain.repository.OutputApiRepository;
import com.zoomout.backend.domain.repository.ResourceRepository;
import com.zoomout.backend.exception.NotFoundException;
import com.zoomout.backend.web.dto.ComponentDto;
import com.zoomout.backend.web.dto.ComponentInfoDto;
import com.zoomout.backend.web.dto.InputApiDto;
import com.zoomout.backend.web.dto.OutputApiInfoDto;
import com.zoomout.backend.web.dto.ResourceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ComponentDocService {
	
	private final ComponentRepository componentRepository;
	
	private final ResourceRepository resourceRepository;
	
	private final InputApiRepository inputApiRepository;
	
	private final OutputApiRepository outputApiRepository;
	
	private final ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public ComponentDto getComponent(long id) {
		return componentRepository.findById(id)
				.map(component -> modelMapper.map(component, ComponentDto.class))
				.orElseThrow(() -> new NotFoundException(String.format("Not found component with id: '%d'", id)));
	}
	
	@Transactional(readOnly = true)
	public Page<ComponentInfoDto> getComponentsByApplicationId(long id, Pageable pageable) {
		return componentRepository.findByApplicationId(id, pageable)
				.map(component -> modelMapper.map(component, ComponentInfoDto.class));
	}
	
	public void deleteComponent(long id) {
		componentRepository.deleteById(id);
	}
	
	public List<ResourceDto> getComponentResources(long id) {
		return resourceRepository.findByComponentId(id)
				.stream()
				.map(resource -> modelMapper.map(resource, ResourceDto.class))
				.collect(Collectors.toList());
	}
	
	public String getComponentDefinition(long id) {
		return componentRepository.findById(id)
				.map(Component::getDefinition)
				.orElseThrow(() -> new NotFoundException(String.format("Not found component with id: '%d'", id)));
	}
	
	public List<InputApiDto> getComponentInputApis(long id) {
		return inputApiRepository.findByComponentId(id)
				.stream()
				.map(inputApi -> modelMapper.map(inputApi, InputApiDto.class))
				.collect(Collectors.toList());
	}
	
	public List<OutputApiInfoDto> getComponentOutputApis(long id) {
		return outputApiRepository.findByComponentId(id)
				.stream()
				.map(outputApi -> modelMapper.map(outputApi, OutputApiInfoDto.class))
				.collect(Collectors.toList());
	}
}

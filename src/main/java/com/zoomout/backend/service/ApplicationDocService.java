package com.zoomout.backend.service;

import com.zoomout.backend.config.AppDocZoomOutProperties;
import com.zoomout.backend.domain.model.Application;
import com.zoomout.backend.domain.model.Component;
import com.zoomout.backend.domain.repository.ApplicationRepository;
import com.zoomout.backend.domain.repository.ComponentRepository;
import com.zoomout.backend.domain.repository.GroupRepository;
import com.zoomout.backend.domain.repository.LinkRepository;
import com.zoomout.backend.domain.repository.ResourceRepository;
import com.zoomout.backend.exception.NotFoundException;
import com.zoomout.backend.service.loader.ElementLoaderService;
import com.zoomout.backend.service.loader.RestAppDocLoaderAdapter;
import com.zoomout.backend.web.dto.ApplicationDto;
import com.zoomout.backend.web.dto.ApplicationInfoDto;
import com.zoomout.model.Element;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationDocService {
	
	public static final String DEFAULT_APPLICATION_NAME = "default";
	private final ApplicationRepository applicationRepository;
	
	private final ComponentRepository componentRepository;
	
	private final LinkRepository linkRepository;
	
	private final GroupRepository groupRepository;
	
	private final ResourceRepository resourceRepository;
	
	private final ElementLoaderService elementLoaderService;
	
	private final RestAppDocLoaderAdapter restAppDocLoaderAdapter;
	
	private final ModelMapper modelMapper;
	
	public void addComponents(long id, AppDocZoomOutProperties appDocZoomOutProperties) {
	
	}
	
	public ApplicationInfoDto addApplicationDoc(Application application) {
		if (application != null) {
			Application applicationPersisted = applicationRepository.save(application);
			return modelMapper.map(applicationPersisted, ApplicationInfoDto.class);
		} else {
			return null;
		}
	}
	
	public Application createDefaultApplicationDoc(AppDocZoomOutProperties appDocZoomOutProperties) {
		Application application = Application.builder()
				.name(DEFAULT_APPLICATION_NAME)
				.description("Default application").build();
		return populateApplication(application, appDocZoomOutProperties);
	}
	
	private Application populateApplication(Application application, AppDocZoomOutProperties appDocZoomOutProperties) {
		List<String> urls = appDocZoomOutProperties.getUrls();
		urls.forEach(url -> {
			try {
				List<Element> elements = restAppDocLoaderAdapter.loadFrom(url);
				Component component = elementLoaderService.convert(elements);
				application.addComponent(component);
			} catch (Exception e) {
				log.error("Failed read from url: '{}' - '{}'", url, e.getMessage());
			}
		});
		return application;
	}
	
	public Map<String, List<Element>> getElementsByUrl(Collection<String> urls) {
		Map<String, List<Element>> map = new HashMap<>();
		urls.forEach(url -> {
			try {
				List<Element> elements = restAppDocLoaderAdapter.loadFrom(url);
				map.put(url, elements);
			} catch (Exception e) {
				log.error("Failed read from url: '{}' - '{}'", url, e.getMessage());
			}
		});
		return map;
	}
	
	@Transactional(readOnly = true)
	public Page<ApplicationInfoDto> getApplicationsInformation(Pageable pageable) {
		return applicationRepository.findAll(pageable)
				.map(application -> modelMapper.map(application, ApplicationInfoDto.class));
	}
	
	@Transactional(readOnly = true)
	public ApplicationDto getApplication(long id) {
		return applicationRepository.findById(id)
				.map(application -> modelMapper.map(application, ApplicationDto.class))
				.orElseThrow(() -> new NotFoundException(String.format("Not found component with id: '%d'", id)));
	}
	
	public void deleteApplication(long id) {
		applicationRepository.deleteById(id);
	}
}

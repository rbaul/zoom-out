package com.zoomout.backend.service;

import com.zoomout.backend.domain.repository.LinkRepository;
import com.zoomout.backend.exception.NotFoundException;
import com.zoomout.backend.web.dto.LinkInfoDto;
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
public class LinkDocService {
	
	private final LinkRepository linkRepository;
	
	private final ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public LinkInfoDto getLink(long id) {
		return linkRepository.findById(id)
				.map(component -> modelMapper.map(component, LinkInfoDto.class))
				.orElseThrow(() -> new NotFoundException(String.format("Not found link with id: '%d'", id)));
	}
	
	@Transactional(readOnly = true)
	public Page<LinkInfoDto> getLinksByApplicationId(long id, Pageable pageable) {
		return linkRepository.findByApplicationId(id, pageable)
				.map(link -> modelMapper.map(link, LinkInfoDto.class));
	}
	
}

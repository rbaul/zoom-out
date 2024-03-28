package com.zoomout.backend.service;

import com.zoomout.backend.domain.repository.GroupRepository;
import com.zoomout.backend.exception.NotFoundException;
import com.zoomout.backend.web.dto.GroupDto;
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
public class GroupDocService {
	
	private final GroupRepository groupRepository;
	
	private final ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public GroupDto getGroup(long id) {
		return groupRepository.findById(id)
				.map(component -> modelMapper.map(component, GroupDto.class))
				.orElseThrow(() -> new NotFoundException(String.format("Not found group with id: '%d'", id)));
	}
	
	@Transactional(readOnly = true)
	public Page<GroupDto> getGroupsByApplicationId(long id, Pageable pageable) {
		return groupRepository.findByApplicationId(id, pageable)
				.map(group -> modelMapper.map(group, GroupDto.class));
	}
	
	public void deleteGroup(long id) {
		groupRepository.deleteById(id);
	}
	
}

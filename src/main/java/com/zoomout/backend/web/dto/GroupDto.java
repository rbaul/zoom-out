package com.zoomout.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GroupDto {
	private Long id;
	
	private String name;
	
	private String description;
	
	private Set<Long> componentIds;
	
	private Set<Long> groupIds;
}

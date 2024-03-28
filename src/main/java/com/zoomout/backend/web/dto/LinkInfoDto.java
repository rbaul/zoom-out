package com.zoomout.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LinkInfoDto {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long fromId;
	
	private Long toId;
}

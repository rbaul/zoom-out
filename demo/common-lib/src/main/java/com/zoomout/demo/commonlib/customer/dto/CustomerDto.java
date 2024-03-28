package com.zoomout.demo.commonlib.customer.dto;

import com.zoomout.demo.commonlib.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerDto {
	@Null(groups = ValidationGroups.Create.class)
	private String id;
	
	@NotBlank
	private String name;
	
	private String familyName;
	
	private String address;
	
}
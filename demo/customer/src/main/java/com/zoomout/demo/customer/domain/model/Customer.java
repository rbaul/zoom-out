package com.zoomout.demo.customer.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document
public class Customer {
	@Id
	private String id;
	
	@NotBlank
	private String name;
	
	private String familyName;
	
	private String address;
	
	@Version
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Long version;
}
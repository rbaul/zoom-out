package com.zoomout.demo.product.domain.model;

import com.zoomout.demo.commonlib.product.dto.CategoryType;
import com.zoomout.demo.commonlib.product.dto.ProductState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document
public class Product {
	@Id
	private String id;
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	@Range
	private Double price;
	
	@NotNull
	@Range
	private Long quantity;
	
	@NotNull
	private ProductState state = ProductState.REGULAR;

//	private Double discount; // need another microservices
	
	private String image;
	
	private CategoryType category;
	
	@Version
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Long version;
}
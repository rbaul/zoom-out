package com.zoomout.demo.commonlib.product.dto;

import com.zoomout.demo.commonlib.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductDto {
	@Null(groups = ValidationGroups.Create.class)
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
	
	
	private String image;
	
	@NotNull
	private CategoryType category;
	
}
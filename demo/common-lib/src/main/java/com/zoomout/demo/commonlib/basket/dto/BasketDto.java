package com.zoomout.demo.commonlib.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BasketDto {
	@NotNull
	private String customerId;
	
	private Set<@NotNull String> coupons;
	
	@Valid
	private List<@Valid ProductBasketDto> productIds;
	
	private Double totalPrice = 0.0;
	
}
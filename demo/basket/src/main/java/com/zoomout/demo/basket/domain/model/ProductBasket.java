package com.zoomout.demo.basket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class ProductBasket {
	
	@Id
	@GeneratedValue
	private Short id;
	
	@NotBlank
	private String productId;
	
	@NotNull
	private Boolean available = true;
	
	@NotNull
	@Range(min = 1)
	private Long quantity;
	
	@ManyToOne
	private Basket basket;
	
	public void increaseQuantityBy(int increaseNumber) {
		quantity += increaseNumber;
	}
}
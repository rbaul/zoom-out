package com.zoomout.demo.basket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Basket {
	@Id
	@GeneratedValue
	private String customerId;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> coupons = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "basket")
	private List<ProductBasket> products;
	
	@NotNull
	@Range
	private Double totalPrice = 0.0;
	
	public void setProducts(List<ProductBasket> products) {
		if (!CollectionUtils.isEmpty(this.products)) {
			this.products.forEach(this::removeProduct);
		}
		
		if (!CollectionUtils.isEmpty(products)) {
			products.forEach(this::addProduct);
		}
	}
	
	public void addProduct(ProductBasket product) {
		if (products == null) {
			products = new ArrayList<>();
		}
		product.setBasket(this);
		products.add(product);
	}
	
	public void removeProduct(ProductBasket product) {
		if (products != null) {
			products.remove(product);
			product.setBasket(null);
		}
	}
	
}
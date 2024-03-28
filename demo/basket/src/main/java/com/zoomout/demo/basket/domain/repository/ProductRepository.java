package com.zoomout.demo.basket.domain.repository;

import com.zoomout.demo.basket.domain.model.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductBasket, Short> {
	Optional<ProductBasket> findByProductIdAndBasketCustomerId(String productId, String customerId);
	
	boolean deleteByProductIdAndBasketCustomerId(String productId, String customerId);
}

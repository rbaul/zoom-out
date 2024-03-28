package com.zoomout.demo.product.domain.repository;

import com.zoomout.demo.product.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
	
	Optional<Product> findByName(String name);
	
}

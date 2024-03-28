package com.zoomout.demo.basket.domain.repository;

import com.zoomout.demo.basket.domain.model.Basket;
import com.zoomout.demo.basket.domain.model.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, String> {
}

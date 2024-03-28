package com.zoomout.demo.basket.service;

import com.zoomout.demo.basket.domain.model.Basket;
import com.zoomout.demo.basket.domain.model.ProductBasket;
import com.zoomout.demo.basket.domain.repository.BasketRepository;
import com.zoomout.demo.basket.domain.repository.ProductRepository;
import com.zoomout.demo.commonlib.basket.dto.BasketDto;
import com.zoomout.demo.commonlib.basket.dto.ProductBasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketService {
	
	private final BasketRepository basketRepository;
	
	private final ProductRepository productRepository;
	
	private final ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public BasketDto get(String customerId) {
		return modelMapper.map(getById(customerId), BasketDto.class);
	}
	
	@Transactional
	public BasketDto create(BasketDto basketDto) {
		Basket basket = basketRepository.save(modelMapper.map(basketDto, Basket.class));
		return modelMapper.map(basket, BasketDto.class);
	}
	
	@Transactional
	public BasketDto addProduct(String customerId, ProductBasketDto productDto) {
		Basket basketPersisted = getById(customerId);
		
		List<ProductBasket> products = basketPersisted.getProducts();
		Optional<ProductBasket> productOptional = products.stream()
				.filter(productBasket -> Objects.equals(productBasket.getProductId(), productDto.getProductId())).findAny();
		
		if (productOptional.isPresent()) {
			ProductBasket productBasket = productOptional.get();
			productBasket.increaseQuantityBy(1);
		} else {
			basketPersisted.addProduct(modelMapper.map(productDto, ProductBasket.class));
		}
		return modelMapper.map(basketPersisted, BasketDto.class);
	}
	
	@Transactional
	public BasketDto removeProduct(String customerId, ProductBasketDto productDto) {
		Basket basketPersisted = getById(customerId);
		
		List<ProductBasket> products = basketPersisted.getProducts();
		Optional<ProductBasket> productOptional = products.stream()
				.filter(productBasket -> Objects.equals(productBasket.getProductId(), productDto.getProductId())).findAny();
		
		if (productOptional.isPresent()) {
			ProductBasket productBasket = productOptional.get();
			if (productBasket.getQuantity() == 1) {
				basketPersisted.removeProduct(modelMapper.map(productDto, ProductBasket.class));
			} else {
				productBasket.increaseQuantityBy(-1);
			}
		}
		return modelMapper.map(basketPersisted, BasketDto.class);
	}
	
	@Transactional
	public void delete(String customerId) {
		if (!basketRepository.existsById(customerId)) {
			throw new EmptyResultDataAccessException("No found product with id: " + customerId, 1);
		}
		basketRepository.deleteById(customerId);
	}
	
	private Basket getById(String id) {
		return basketRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException("No found basket with id: " + id, 1));
	}
}

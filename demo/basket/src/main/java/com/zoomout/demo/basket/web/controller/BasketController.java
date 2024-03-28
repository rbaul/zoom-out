package com.zoomout.demo.basket.web.controller;

import com.zoomout.demo.basket.service.BasketService;
import com.zoomout.demo.commonlib.ValidationGroups;
import com.zoomout.demo.commonlib.basket.dto.BasketDto;
import com.zoomout.demo.commonlib.basket.dto.ProductBasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/baskets")
public class BasketController {
	
	private final BasketService basketService;
	
	@GetMapping("{customerId}")
	public BasketDto getBasket(@PathVariable String customerId) {
		return basketService.get(customerId);
	}
	
	@PostMapping
	public BasketDto create(
			@Validated(ValidationGroups.Create.class) @RequestBody BasketDto basketDto) {
		return basketService.create(basketDto);
	}
	
	@PutMapping("{customerId}/product")
	public BasketDto addProduct(@PathVariable String customerId,
								@Validated(ValidationGroups.Update.class) @RequestBody ProductBasketDto basketDto) {
		return basketService.addProduct(customerId, basketDto);
	}
	
	@DeleteMapping("{customerId}/product")
	public BasketDto deleteProduct(@PathVariable String customerId,
								   @Validated(ValidationGroups.Update.class) @RequestBody ProductBasketDto basketDto) {
		return basketService.removeProduct(customerId, basketDto);
	}
	
	@DeleteMapping("{customerId}")
	public void delete(@PathVariable String productId) {
		basketService.delete(productId);
	}
	
}

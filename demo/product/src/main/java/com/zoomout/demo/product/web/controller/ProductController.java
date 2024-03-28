package com.zoomout.demo.product.web.controller;

import com.zoomout.demo.commonlib.ValidationGroups;
import com.zoomout.demo.commonlib.product.dto.ProductDto;
import com.zoomout.demo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("{productId}")
	public ProductDto getProduct(@PathVariable String productId) {
		return productService.get(productId);
	}
	
	@PostMapping
	public ProductDto create(
			@Validated(ValidationGroups.Create.class) @RequestBody ProductDto productDto) {
		return productService.create(productDto);
	}
	
	@PutMapping("{productId}")
	public ProductDto update(@PathVariable String productId,
							 @Validated(ValidationGroups.Update.class) @RequestBody ProductDto productDto) {
		return productService.update(productId, productDto);
	}
	
	@DeleteMapping("{productId}")
	public void delete(@PathVariable String productId) {
		productService.delete(productId);
	}
	
	@GetMapping("search")
	public Page<ProductDto> fetch(@RequestParam(required = false) String filter, @PageableDefault Pageable pageable) {
		return productService.search(filter, pageable);
	}
}

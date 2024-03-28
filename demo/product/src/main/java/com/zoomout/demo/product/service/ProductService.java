package com.zoomout.demo.product.service;

import com.zoomout.demo.commonlib.product.dto.ProductDto;
import com.zoomout.demo.product.domain.model.Product;
import com.zoomout.demo.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	private final ModelMapper modelMapper;
	
	public ProductDto get(String productId) {
		return modelMapper.map(getProductById(productId), ProductDto.class);
	}
	
	public ProductDto create(ProductDto productDto) {
		Product product = productRepository.save(modelMapper.map(productDto, Product.class));
		return modelMapper.map(product, ProductDto.class);
	}
	
	public ProductDto update(String productId, ProductDto productDto) {
		Product product = getProductById(productId);
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setState(productDto.getState());
		return modelMapper.map(product, ProductDto.class);
	}
	
	public void delete(String productId) {
		if (!productRepository.existsById(productId)) {
			throw new EmptyResultDataAccessException("No found product with id: " + productId, 1);
		}
		productRepository.deleteById(productId);
	}
	
	public List<ProductDto> getAll() {
		return productRepository.findAll().stream()
				.map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}
	
	public Page<ProductDto> search(String filter, Pageable pageable) {
//		PathBuilder<Product> productPathBuilder = new PathBuilder<>(Product.class, "product");
//		Predicate predicate = productPathBuilder.getString(Product.Fields.name).contains(filter)
//				.or(productPathBuilder.getString(Product.Fields.description).contains(filter));
		
		return productRepository.findAll(pageable)
				.map(product -> modelMapper.map(product, ProductDto.class));
	}
	
	private Product getProductById(String productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new EmptyResultDataAccessException("No found product with id: " + productId, 1));
	}
}

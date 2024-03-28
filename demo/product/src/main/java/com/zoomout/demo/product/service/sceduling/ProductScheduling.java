package com.zoomout.demo.product.service.sceduling;

import com.zoomout.demo.commonlib.dto.NotificationTypeDto;
import com.zoomout.demo.commonlib.product.api.ProductAsyncApi;
import com.zoomout.demo.commonlib.product.dto.ProductDto;
import com.zoomout.demo.commonlib.product.dto.ProductNotificationDto;
import com.zoomout.demo.commonlib.util.RestResponsePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductScheduling {
	private final RabbitTemplate rabbitTemplate;
	
	private final RestTemplate restTemplate;
	
	@Scheduled(fixedDelayString = "PT20S")
	public void publishNewProduct() {
		ProductDto productDto = ProductDto.builder()
				.id(RandomStringUtils.randomAlphanumeric(20))
				.name(RandomStringUtils.randomAlphabetic(5)).build();
		
		rabbitTemplate.convertAndSend(ProductAsyncApi.PRODUCT_TOPIC_EXCHANGE, ProductAsyncApi.PRODUCT_ROUTING_KEY,
				ProductNotificationDto.builder().notificationType(NotificationTypeDto.OBJECT_CREATION).content(productDto).build());
	}
	
	@Scheduled(fixedDelayString = "PT10S")
	private void retrieveAllProducts() {
		ResponseEntity<RestResponsePage<ProductDto>> exchange = restTemplate.exchange("http://localhost:9001/api/v1/products/search", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<ProductDto>>() {
		});
		log.info(">>> Retrieve products: '{}'", exchange.getBody());
	}
}

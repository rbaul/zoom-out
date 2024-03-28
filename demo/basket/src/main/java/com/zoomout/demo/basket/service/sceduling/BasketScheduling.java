package com.zoomout.demo.basket.service.sceduling;

import com.zoomout.demo.commonlib.basket.api.BasketAsyncApi;
import com.zoomout.demo.commonlib.basket.dto.BasketDto;
import com.zoomout.demo.commonlib.basket.dto.BasketNotificationDto;
import com.zoomout.demo.commonlib.customer.dto.CustomerDto;
import com.zoomout.demo.commonlib.dto.NotificationTypeDto;
import com.zoomout.demo.commonlib.product.dto.ProductDto;
import com.zoomout.demo.commonlib.util.RestResponsePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketScheduling {
	private final RabbitTemplate rabbitTemplate;
	
	private final RestTemplate restTemplate;
	
	private final RequestEntity requestEntity;
	
	@Scheduled(fixedDelayString = "PT20S")
	public void publishChangeBasket() {
		BasketDto basketDto = BasketDto.builder()
				.customerId(RandomStringUtils.randomAlphanumeric(20))
//				.productIds(List.of(
//						ProductBasketDto.builder().productId(RandomStringUtils.randomAlphanumeric(20)).quantity(2L).build())
//				)
		.build();
		rabbitTemplate.convertAndSend(BasketAsyncApi.BASKET_TOPIC_EXCHANGE, BasketAsyncApi.BASKET_ROUTING_KEY,
				BasketNotificationDto.builder().notificationType(NotificationTypeDto.ATTRIBUTE_VALUE_CHANGE).content(basketDto).build());
	}
	
	@Scheduled(fixedDelayString = "PT10S")
	private void retrieveAllProducts() {
		ResponseEntity<RestResponsePage<ProductDto>> exchange = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<RestResponsePage<ProductDto>>() {
		});
//		RequestEntity<?> requestEntity =
//		ResponseEntity<RestResponsePage<ProductDto>> exchange = restTemplate.exchange("http://localhost:9001/api/v1/products/search", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<ProductDto>>() {
//		});
		log.info(">>> Retrieve products: '{}'", exchange.getBody());
	}
	
	@Scheduled(fixedDelayString = "PT10S")
	private void retrieveAllCustomers() {
		ResponseEntity<RestResponsePage<CustomerDto>> exchange = restTemplate.exchange("http://localhost:9000/api/v1/customers/search", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<CustomerDto>>() {
		});
		log.info(">>> Retrieve customers: '{}'", exchange.getBody());
	}
}

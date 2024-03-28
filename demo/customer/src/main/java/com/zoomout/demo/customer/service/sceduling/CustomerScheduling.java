package com.zoomout.demo.customer.service.sceduling;

import com.zoomout.demo.commonlib.customer.api.CustomerAsyncApi;
import com.zoomout.demo.commonlib.customer.dto.CustomerDto;
import com.zoomout.demo.commonlib.customer.dto.CustomerNotificationDto;
import com.zoomout.demo.commonlib.dto.NotificationTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerScheduling {
	private final RabbitTemplate rabbitTemplate;
	
	private final RestTemplate restTemplate;
	
	@Scheduled(fixedDelayString = "PT20S")
	public void publishNewCustomer() {
		CustomerDto customerDto = CustomerDto.builder()
				.id(RandomStringUtils.randomAlphanumeric(20))
				.name(RandomStringUtils.randomAlphabetic(5)).build();
		
		rabbitTemplate.convertAndSend(CustomerAsyncApi.CUSTOMER_TOPIC_EXCHANGE, CustomerAsyncApi.CUSTOMER_ROUTING_KEY,
				CustomerNotificationDto.builder().notificationType(NotificationTypeDto.OBJECT_CREATION).content(customerDto).build());
	}

//	@Scheduled(fixedDelayString = "PT10S")
//	private void retrieveAllProducts() {
//		ResponseEntity<RestResponsePage<ProductDto>> exchange = restTemplate.exchange("http://localhost:9001/api/v1/products/search", HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<ProductDto>>() {
//		});
//		log.info(">>> Retrieve products: '{}'", exchange.getBody());
//	}
}

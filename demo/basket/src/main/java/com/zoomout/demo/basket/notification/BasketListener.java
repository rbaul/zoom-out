package com.zoomout.demo.basket.notification;

import com.zoomout.demo.commonlib.customer.api.CustomerAsyncApi;
import com.zoomout.demo.commonlib.customer.dto.CustomerNotificationDto;
import com.zoomout.demo.commonlib.product.api.ProductAsyncApi;
import com.zoomout.demo.commonlib.product.dto.ProductNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasketListener {
	
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = ProductAsyncApi.PRODUCT_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
			key = ProductAsyncApi.PRODUCT_ROUTING_KEY,
			value = @Queue(value = "basket-product-queue", durable = "true")))
	public void receiveProduct(ProductNotificationDto productDto) {
		log.info(">>> Received Product: '{}'", productDto);
	}
	
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = CustomerAsyncApi.CUSTOMER_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
			key = CustomerAsyncApi.CUSTOMER_ROUTING_KEY,
			value = @Queue(value = "basket-customer-queue", durable = "true")))
	public void receiveCustomer(CustomerNotificationDto customerDto) {
		log.info(">>> Received Customer: '{}'", customerDto);
	}
}

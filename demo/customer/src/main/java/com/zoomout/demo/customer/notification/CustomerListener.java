package com.zoomout.demo.customer.notification;

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
public class CustomerListener {
	
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = ProductAsyncApi.PRODUCT_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
			key = ProductAsyncApi.PRODUCT_ROUTING_KEY,
			value = @Queue(value = "customer-product-queue", durable = "true")))
	public void receiveProduct(ProductNotificationDto productDto) {
		log.info(">>> Received Product: '{}'", productDto);
	}
}

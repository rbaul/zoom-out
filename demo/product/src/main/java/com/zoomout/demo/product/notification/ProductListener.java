package com.zoomout.demo.product.notification;

import com.zoomout.demo.commonlib.basket.dto.BasketNotificationDto;
import com.zoomout.demo.commonlib.product.api.ProductAsyncApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductListener {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = ProductAsyncApi.PRODUCT_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
			key = ProductAsyncApi.PRODUCT_ROUTING_KEY,
			value = @Queue(value = "product-product-queue", durable = "true")))
	public void receiveProduct(BasketNotificationDto productDto) {
		log.info(">>> Received product: '{}'", productDto);
	}
}

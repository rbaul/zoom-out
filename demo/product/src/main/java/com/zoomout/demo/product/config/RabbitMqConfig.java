package com.zoomout.demo.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoomout.demo.commonlib.product.api.ProductAsyncApi;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(ProductAsyncApi.PRODUCT_TOPIC_EXCHANGE);
	}
	
	/////////////// RabitMQ Settings for JSON objects
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(objectMapper));
		return rabbitTemplate;
	}
	
	public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
		jackson2JsonMessageConverter.setAssumeSupportedContentType(false);
		return jackson2JsonMessageConverter;
	}
	
	@Autowired
	public void rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory, ObjectMapper objectMapper) {
		rabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
	}
}

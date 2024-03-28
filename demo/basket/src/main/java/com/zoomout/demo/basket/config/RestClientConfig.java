package com.zoomout.demo.basket.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
public class RestClientConfig {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	
	@Bean
	public RequestEntity requestEntityProductSearch() {
		return new RequestEntity<>(null, HttpMethod.GET, URI.create("http://localhost:9001/api/v1/products/search"));
	}
}

package com.zoomout.demo.commonlib.customer.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerAsyncApi {
	public static final String CUSTOMER_TOPIC_EXCHANGE = "customer-exchange";
	public static final String CUSTOMER_ROUTING_KEY = "change.customer";
}

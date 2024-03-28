package com.zoomout.demo.commonlib.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryType {
	ADULT("Adult"),
	KID("kid");
	
	@Getter
	private final String value;
}

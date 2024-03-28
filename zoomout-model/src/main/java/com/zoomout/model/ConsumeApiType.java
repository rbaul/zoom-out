package com.zoomout.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsumeApiType {
	REST,
	RABBIT_MQ,
	KAFKA
}

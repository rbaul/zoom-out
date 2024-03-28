package com.zoomout.demo.commonlib.product.dto;

import com.zoomout.demo.commonlib.dto.NotificationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class ProductNotificationDto extends NotificationDto<ProductDto> {
	
}
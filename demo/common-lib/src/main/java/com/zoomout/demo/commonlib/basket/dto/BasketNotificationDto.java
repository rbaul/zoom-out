package com.zoomout.demo.commonlib.basket.dto;

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
public class BasketNotificationDto extends NotificationDto<BasketDto> {
	
}
package com.zoomout.demo.commonlib.customer.dto;

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
public class CustomerNotificationDto extends NotificationDto<CustomerDto> {
	
}
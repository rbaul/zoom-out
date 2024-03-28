package com.zoomout.demo.customer.web.controller;

import com.zoomout.demo.commonlib.ValidationGroups;
import com.zoomout.demo.commonlib.customer.dto.CustomerDto;
import com.zoomout.demo.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping("{customerId}")
	public CustomerDto getCustomer(@PathVariable String customerId) {
		return customerService.get(customerId);
	}
	
	@PostMapping
	public CustomerDto create(
			@Validated(ValidationGroups.Create.class) @RequestBody CustomerDto customerDto) {
		return customerService.create(customerDto);
	}
	
	@PutMapping("{customerId}")
	public CustomerDto update(@PathVariable String customerId,
							  @Validated(ValidationGroups.Update.class) @RequestBody CustomerDto customerDto) {
		return customerService.update(customerId, customerDto);
	}
	
	@DeleteMapping("{customerId}")
	public void delete(@PathVariable String productId) {
		customerService.delete(productId);
	}
	
	@GetMapping("search")
	public Page<CustomerDto> fetch(@RequestParam(required = false) String filter, @PageableDefault Pageable pageable) {
		return customerService.search(filter, pageable);
	}
}

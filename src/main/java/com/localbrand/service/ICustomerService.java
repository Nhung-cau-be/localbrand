package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.response.CustomerDto;

public interface ICustomerService {
	List<CustomerDto> getAll();
	
	CustomerDto getById(String id);
	
	CustomerDto insert(CustomerDto customerDto);
	
	CustomerDto update(CustomerDto customerDto);
	
	Boolean deleteById(String id);
	
	Boolean isExitsPhoneNumber(String phoneNumber);
	
	Boolean isExitsEmail(String email);
	
	Boolean isExitsPhoneNumberIgnore(String phoneNumber, String customerId);
	
	Boolean isExitsEmailIgnore(String email, String customerId);
	
}

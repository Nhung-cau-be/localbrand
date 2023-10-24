package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.response.CustomerDto;

public interface ICustomerService {
	List<CustomerDto> getAll();
	
	CustomerDto getById(String id);
	
	CustomerDto insert(CustomerDto customerDto);
	
	CustomerDto update(CustomerDto customerDto);
	
	Boolean deleteById(String id);
	
	Boolean isExistPhoneNumber(String phoneNumber);
	
	Boolean isExistEmail(String email);
	
	Boolean isExistPhoneNumberIgnore(String phoneNumber, String customerId);
	
	Boolean isExistEmailIgnore(String email, String customerId);
	
}

package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.response.CustomerDto;

public interface ICustomerService {
	List<CustomerDto> getAll();
	
	CustomerDto insert(CustomerDto customerDto);
	
	CustomerDto update(CustomerDto customerDto);
	
	Boolean deleteById(String id);
	
	List<CustomerDto> searchByName(String name);
	
	List<CustomerDto> searchByPhoneNumber(String sdt);
	
	Boolean isUsingPhoneNumber(String sdt);
	
	Boolean isUsingEmail(String email);
}

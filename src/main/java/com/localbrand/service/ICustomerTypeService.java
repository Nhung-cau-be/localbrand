package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.CustomerTypeDto;


public interface ICustomerTypeService {
	List<CustomerTypeDto> getAll();
	
	CustomerTypeDto insert(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto update(CustomerTypeDto providerDto);
	
	Boolean deleteById(String id);
	
	Boolean isUsingName(String name);
}

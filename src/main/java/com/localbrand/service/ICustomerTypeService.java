package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.CustomerTypeDto;


public interface ICustomerTypeService {
	List<CustomerTypeDto> getAll();
	
	CustomerTypeDto insert(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto update(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto findById (String id);
	
	Boolean deleteById(String id);
	
	Boolean isExistName(String name);
	
	Boolean isExistNameIgnore(String name, String customerTypeId);
	
	Boolean isUsing(String id);
	
	Boolean isExistStandardPoint(Integer standardPoint);
	
	Boolean isExistStandardPointIgnore(Integer standardPoint, String customerTypeId);
	
}

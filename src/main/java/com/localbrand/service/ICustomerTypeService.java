package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.CustomerTypeDto;


public interface ICustomerTypeService {
	List<CustomerTypeDto> getAll();
	
	CustomerTypeDto insert(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto update(CustomerTypeDto providerDto);
	
	Boolean deleteById(String id);
	
	Boolean isExitsName(String name);
	
	Boolean isExitsNameIgnore(String name, String customerTypeId);
	
	boolean isUsing(String id);
	
	Boolean isExitsStandardPoint(Integer standardPoint);
	
	Boolean isExitsStandardPointIgnore(Integer standardPoint, String customerTypeId);
}

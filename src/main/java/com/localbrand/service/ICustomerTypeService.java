package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.CustomerTypeDto;


public interface ICustomerTypeService {
	List<CustomerTypeDto> getAll();
	
	CustomerTypeDto insert(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto update(CustomerTypeDto customerTypeDto);
	
	CustomerTypeDto findById (String id);
	
	boolean deleteById(String id);
	
	boolean isExistName(String name);
	
	boolean isExistNameIgnore(String name, String customerTypeId);
	
	boolean isUsing(String id);
	
	boolean isExistStandardPoint(Integer standardPoint);
	
	boolean isExistStandardPointIgnore(Integer standardPoint, String customerTypeId);
	
	BaseSearchDto<List<CustomerTypeDto>> findAll(BaseSearchDto<List<CustomerTypeDto>> searchDto);

}

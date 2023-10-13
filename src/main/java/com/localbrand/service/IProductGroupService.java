package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.ProductGroupDto;

public interface IProductGroupService {
	
	List<ProductGroupDto> getAll();
	
	ProductGroupDto getById(String id);
	
	ProductGroupDto insert(ProductGroupDto productGroupDto);
	
	ProductGroupDto update(ProductGroupDto productGroupDto);
	
	Boolean deleteById(String id);

	Boolean isUsingName(String name);
}

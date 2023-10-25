package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.ProductGroupDto;

public interface IProductGroupService {
	
	List<ProductGroupDto> getAll();
	
	BaseSearchDto<List<ProductGroupDto>> findAll(BaseSearchDto<List<ProductGroupDto>> searchDto);
	
	ProductGroupDto getById(String id);
	
	ProductGroupDto insert(ProductGroupDto productGroupDto);
	
	ProductGroupDto update(ProductGroupDto productGroupDto);
	
	boolean deleteById(String id);

	boolean isExistName(String name);

	boolean isExistNameIgnore(String name, String productGroupId);
}

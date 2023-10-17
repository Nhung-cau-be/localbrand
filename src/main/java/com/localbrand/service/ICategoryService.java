package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CategoryFullDto;

public interface ICategoryService {
	
	List<CategoryDto> getAll();

	BaseSearchDto<List<CategoryDto>> findAll(BaseSearchDto<List<CategoryDto>> searchDto);
	
	CategoryDto getById(String id);

	CategoryFullDto getFull(String id);
	
	CategoryDto insert(CategoryDto categoryDto);

	CategoryDto update(CategoryDto categoryDto);
	
	Boolean deleteById(String id);
	
	boolean isUsing(String id);
	
	boolean isExistName(String name);
	
	boolean isExistNameIgnore(String name, String categoryId);
}

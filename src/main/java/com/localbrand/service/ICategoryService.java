package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CategoryFullDto;
import com.localbrand.dtos.response.ProductGroupDto;

public interface ICategoryService {
	
	List<CategoryDto> getAll();
	
	CategoryDto getById(String id);

	CategoryFullDto getFull(String id);
	
	CategoryDto insert(CategoryDto categoryDto);

	CategoryDto update(CategoryDto categoryDto);
	
	Boolean deleteById(String id);
	
	boolean isUsing(String id);
	
	boolean isExistName(String name);
	
	boolean isExistNameIgnore(String name, String categoryId);
}

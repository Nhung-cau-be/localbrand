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
	
	List<CategoryFullDto> getAllFull();
	
	CategoryDto insert(CategoryDto categoryDto);

	CategoryDto update(CategoryDto categoryDto);
	
	Boolean deleteById(String id);
	
	boolean isUsing(String id);
	
	boolean isExistCode(String code);
	
	boolean isExistCodeIgnore(String code, String categoryId);
}

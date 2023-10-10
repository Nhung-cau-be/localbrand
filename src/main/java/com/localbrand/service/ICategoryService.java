package com.localbrand.service;

import java.util.UUID;
import java.util.List;

import com.localbrand.dal.entity.Category;
import com.localbrand.dtos.response.CategoryDto;

public interface ICategoryService {
	
	List<CategoryDto> getAll();
	
	CategoryDto getById(String id);
	
	CategoryDto add(CategoryDto categoryDto);
	
	Boolean deleteById(String id);
	
	CategoryDto editById(CategoryDto categoryDto);
}

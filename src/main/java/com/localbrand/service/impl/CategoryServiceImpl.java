package com.localbrand.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.repository.ICategoryRepository;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.mappers.ICategoryDtoMapper;
import com.localbrand.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Override
	public List<CategoryDto> getAll() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = ICategoryDtoMapper.INSTANCE.toCategoryDtos(categories);
		
		return categoryDtos;
	}
}

package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

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

	@Override
	public CategoryDto getById(String id) {
		Category category = categoryRepository.getById(id);
		if(category != null)
		{
			CategoryDto categoryDto = ICategoryDtoMapper.INSTANCE.toCategoryDto(category);
			return categoryDto;
		}
		else {
			return null;
		}
	}

	@Override
	public CategoryDto add(CategoryDto categoryDto) {
		
		Category category = ICategoryDtoMapper.INSTANCE.toCategory(categoryDto);
		
		category.setId(UUID.randomUUID().toString());
		
		Category newCategory = categoryRepository.save(category);

		CategoryDto newCategoryDto = ICategoryDtoMapper.INSTANCE.toCategoryDto(newCategory);

		return newCategoryDto;
	}

	@Override
	public Boolean deleteById(String id) {
		
		Category category = categoryRepository.getById(id);

		if (category != null)
		{
			categoryRepository.delete(category);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public CategoryDto editById(CategoryDto categoryDto) {
		
		String id = categoryDto.getId();
		
		Category category = categoryRepository.getById(id);
		
		if(category != null)
		{
			category.setName(categoryDto.getName());
			categoryRepository.save(category);
			return ICategoryDtoMapper.INSTANCE.toCategoryDto(category);
		}
		else {
			return null;
		}
	}
}

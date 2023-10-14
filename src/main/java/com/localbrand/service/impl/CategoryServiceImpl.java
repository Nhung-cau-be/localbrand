package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.repository.ICategoryRepository;
import com.localbrand.dal.repository.IProductGroupRepository;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CategoryFullDto;
import com.localbrand.dtos.response.ProductGroupDto;
import com.localbrand.mappers.ICategoryDtoMapper;
import com.localbrand.mappers.IProductGroupDtoMapper;
import com.localbrand.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
	private IProductGroupRepository productGroupRepository;

	@Override
	public List<CategoryDto> getAll() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = ICategoryDtoMapper.INSTANCE.toCategoryDtos(categories);
		return categoryDtos;
	}

	@Override
	public CategoryDto getById(String id) {
		Category category = categoryRepository.findById(id).orElse(null);
		return ICategoryDtoMapper.INSTANCE.toCategoryDto(category);
	}

	@Override
	public CategoryFullDto getFull(String id) {
		try {
			Category category = categoryRepository.findById(id).orElse(null);
			if(category == null)
				return null;
			
			CategoryFullDto categoryFullDto = ICategoryDtoMapper.INSTANCE.toCategoryFullDto(null);
			
			List<ProductGroupDto> productGroupDto = IProductGroupDtoMapper.INSTANCE.toProductGroupDtos(productGroupRepository.findByCategoryId(id));
			categoryFullDto.setProductGroups(productGroupDto);
			
			return categoryFullDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public CategoryDto insert(CategoryDto categoryDto) {
		try {
			categoryDto.setId(UUID.randomUUID().toString());
			Category category = ICategoryDtoMapper.INSTANCE.toCategory(categoryDto);
			categoryRepository.save(category);
			
			return categoryDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto) {
		try {
			Category category = ICategoryDtoMapper.INSTANCE.toCategory(categoryDto);
			categoryRepository.save(category);
			
			return categoryDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean deleteById(String id) {
		try {
			categoryRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean isUsing(String id) {
		return productGroupRepository.countByCategoryId(id) > 0;
	}
	
	@Override
	public boolean isExistName(String name) {
		return categoryRepository.countByName(name) > 0;
	}
	
	@Override
	public boolean isExistNameIgnore(String name, String categoryId) {
		return categoryRepository.countByNameIgnore(name, categoryId) > 0;
	}
}

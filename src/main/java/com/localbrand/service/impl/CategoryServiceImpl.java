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
		try {
			List<Category> categories = categoryRepository.findAll();
			List<CategoryDto> categoryDtos = ICategoryDtoMapper.INSTANCE.toCategoryDtos(categories);

			return categoryDtos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public CategoryDto getById(String id) {
		try {
			Category category = categoryRepository.findById(id).orElse(null);

			if (category != null) {
				CategoryDto categoryDto = ICategoryDtoMapper.INSTANCE.toCategoryDto(category);

				return categoryDto;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public CategoryDto add(CategoryDto categoryDto) {
		try {
			Category category = ICategoryDtoMapper.INSTANCE.toCategory(categoryDto);

			category.setId(UUID.randomUUID().toString());

			Category newCategory = categoryRepository.save(category);

			CategoryDto newCategoryDto = ICategoryDtoMapper.INSTANCE.toCategoryDto(newCategory);

			return newCategoryDto;
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
	public CategoryDto update(CategoryDto categoryDto) {
		try {
			Category category = ICategoryDtoMapper.INSTANCE.toCategory(categoryDto);

			categoryRepository.save(category);

			return ICategoryDtoMapper.INSTANCE.toCategoryDto(category);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public CategoryFullDto getFull(String id) {
		try {
			CategoryFullDto categoryFullDto = new CategoryFullDto();
			CategoryDto categoryDto = getById(id);
			List<ProductGroupDto> productGroupDto = IProductGroupDtoMapper.INSTANCE.toProductGroupDtos(productGroupRepository.findByCategoryId(id));
			
			categoryFullDto.setId(categoryDto.getId());
			categoryFullDto.setName(categoryDto.getName());
			categoryFullDto.setProductGroups(productGroupDto);
			
			return categoryFullDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean isUsing(String id) {
		try {
			long count = productGroupRepository.countByCategoryId(id);
			
			if(count != 0)
			{
				return true;
			}
			return false;
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Boolean usedName(String name) {
		try {
			long count = categoryRepository.countByName(name);
			if(count != 0)
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}

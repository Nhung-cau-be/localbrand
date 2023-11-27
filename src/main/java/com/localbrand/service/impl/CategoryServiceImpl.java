package com.localbrand.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.repository.ICategoryRepository;
import com.localbrand.dal.repository.IProductGroupRepository;
import com.localbrand.dtos.request.BaseSearchDto;
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
		categoryDtos.sort(new Comparator<CategoryDto>() {
			@Override
			public int compare(CategoryDto o1, CategoryDto o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return categoryDtos;
	}

	@Override
	public BaseSearchDto<List<CategoryDto>> findAll(BaseSearchDto<List<CategoryDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Category> page = categoryRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(ICategoryDtoMapper.INSTANCE.toCategoryDtos(page.getContent()));

        return searchDto;
	}

	@Override
	public List<CategoryFullDto> getAllFull() {
		try {
			List<Category> categories = categoryRepository.findAll();
			List<CategoryFullDto> categoryFullDtos = ICategoryDtoMapper.INSTANCE.toCategoryFullDtos(categories);
			
			for(CategoryFullDto e : categoryFullDtos)
			{
				CategoryFullDto categoryFullDto = getFull(e.getId());
				e.setProductGroups(categoryFullDto.getProductGroups());
			}
			
			return categoryFullDtos;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
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
			
			CategoryFullDto categoryFullDto = ICategoryDtoMapper.INSTANCE.toCategoryFullDto(category);
			
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
	public boolean isExistCode(String code) {
		return categoryRepository.countByCode(code) > 0;
	}
	
	@Override
	public boolean isExistCodeIgnore(String code, String categoryId) {
		return categoryRepository.countByCodeIgnore(code, categoryId) > 0;
	}
}

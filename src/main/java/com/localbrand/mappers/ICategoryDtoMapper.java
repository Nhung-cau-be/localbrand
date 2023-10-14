package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Category;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CategoryFullDto;

@Mapper
public interface ICategoryDtoMapper {
	
	ICategoryDtoMapper INSTANCE = Mappers.getMapper(ICategoryDtoMapper.class);
	
	Category toCategory(CategoryDto categoryDto);
	
	CategoryDto toCategoryDto(Category category);

	List<CategoryDto> toCategoryDtos(List<Category> categories);
	
	CategoryFullDto toCategoryFullDto(Category category);	
}

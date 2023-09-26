package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Category;
import com.localbrand.dtos.response.CategoryDto;

@Mapper
public interface ICategoryDtoMapper {
	ICategoryDtoMapper INSTANCE = Mappers.getMapper(ICategoryDtoMapper.class);
	
	CategoryDto toCategoryDto(Category category);
	
	List<CategoryDto> toCategoryDtos(List<Category> categories);
}

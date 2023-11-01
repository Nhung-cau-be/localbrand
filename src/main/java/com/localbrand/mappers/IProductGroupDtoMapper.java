package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.ProductGroup;
import com.localbrand.dtos.response.ProductGroupDto;

@Mapper
public interface IProductGroupDtoMapper {
	
	IProductGroupDtoMapper INSTANCE = Mappers.getMapper(IProductGroupDtoMapper.class);
	
	ProductGroup toProductGroup(ProductGroupDto productGroupDto);
	
	ProductGroupDto toProductGroupDto(ProductGroup productGroup);
	
	List<ProductGroupDto> toProductGroupDtos(List<ProductGroup> productGroups);
}

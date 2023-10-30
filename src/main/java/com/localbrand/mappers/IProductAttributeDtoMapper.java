package com.localbrand.mappers;

import com.localbrand.dal.entity.ProductAttribute;
import com.localbrand.dtos.response.ProductAttributeDto;
import com.localbrand.dtos.response.ProductAttributeFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductAttributeDtoMapper {
	IProductAttributeDtoMapper INSTANCE = Mappers.getMapper(IProductAttributeDtoMapper.class);

	ProductAttribute toProductAttribute(ProductAttributeFullDto productAttributeFullDto);

	ProductAttributeDto toProductAttributeDto(ProductAttribute productAttribute);

	List<ProductAttributeDto> toProductAttributeDtos(List<ProductAttribute> productAttributes);
}

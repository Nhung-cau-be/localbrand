package com.localbrand.mappers;

import com.localbrand.dal.entity.ProductAttributeValue;
import com.localbrand.dtos.response.ProductAttributeValueDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductAttributeValueDtoMapper {
	IProductAttributeValueDtoMapper INSTANCE = Mappers.getMapper(IProductAttributeValueDtoMapper.class);

	ProductAttributeValue toProductAttributeValue(ProductAttributeValueDto productAttributeValueDto);

	List<ProductAttributeValue> toProductAttributeValues(List<ProductAttributeValueDto> productAttributeValueDto);

	ProductAttributeValueDto toProductAttributeValueDto(ProductAttributeValue productAttributeValue);

	List<ProductAttributeValueDto> toProductAttributeValueDtos(List<ProductAttributeValue> productAttributeValue);
}

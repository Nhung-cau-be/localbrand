package com.localbrand.mappers;

import com.localbrand.dal.entity.ProductAttributeDetail;
import com.localbrand.dal.entity.ProductSKU;
import com.localbrand.dtos.response.ProductAttributeValueDto;
import com.localbrand.dtos.response.ProductSKUDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductAttributeDetailDtoMapper {
	
	IProductAttributeDetailDtoMapper INSTANCE = Mappers.getMapper(IProductAttributeDetailDtoMapper.class);
}

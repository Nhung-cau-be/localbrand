package com.localbrand.mappers;

import com.localbrand.dal.entity.Product;
import com.localbrand.dal.entity.ProductSKU;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;
import com.localbrand.dtos.response.ProductSKUDto;
import com.localbrand.dtos.response.ProductSKUFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductSKUDtoMapper {
	
	IProductSKUDtoMapper INSTANCE = Mappers.getMapper(IProductSKUDtoMapper.class);

	List<ProductSKUDto> toProductSKUDtos(List<ProductSKU> productSKUs);

    List<ProductSKUFullDto> toProductSKUFullDtos(List<ProductSKU> productSKUS);
}

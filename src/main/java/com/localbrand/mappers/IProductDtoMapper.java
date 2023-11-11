package com.localbrand.mappers;

import com.localbrand.dal.entity.Product;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductDtoMapper {
	
	IProductDtoMapper INSTANCE = Mappers.getMapper(IProductDtoMapper.class);

	Product toProduct(ProductFullDto productFullDto);
	
	ProductDto toProductDto(Product product);
	
	List<ProductDto> toProductDtos(List<Product> products);
}

package com.localbrand.mappers;

import com.localbrand.dal.entity.ProductImage;
import com.localbrand.dtos.response.ProductImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IProductImageDtoMapper {
	IProductImageDtoMapper INSTANCE = Mappers.getMapper(IProductImageDtoMapper.class);

	ProductImage toProductImage(ProductImageDto productImageDto);

	List<ProductImage> toProductImages(List<ProductImageDto> productImageDto);

	ProductImageDto toProductImageDto(ProductImage productImage);

	List<ProductImageDto> toProductImageDtos(List<ProductImage> productImages);
}

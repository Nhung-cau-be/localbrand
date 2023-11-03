package com.localbrand.service;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.ProductAttributeDto;
import com.localbrand.dtos.response.ProductAttributeFullDto;

import java.util.List;

public interface IProductAttributeService {
	List<ProductAttributeDto> getAll();

	BaseSearchDto<List<ProductAttributeDto>> findAll(BaseSearchDto<List<ProductAttributeDto>> searchDto);

	ProductAttributeFullDto getFullById(String id);

	List<ProductAttributeFullDto> getAllFull();

	ProductAttributeFullDto insert(ProductAttributeFullDto productAttribute);

	ProductAttributeFullDto update(ProductAttributeFullDto productAttribute);

	boolean deleteById(String id);

	boolean isExistCode(String code);

	boolean isExistCodeIgnore(String code, String id);
}

package com.localbrand.service;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.ProductSearchDto;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;
import com.localbrand.dtos.response.ProductGroupDto;

import java.util.List;

public interface IProductService {
	List<ProductDto> getAll();

	BaseSearchDto<List<ProductDto>> findAll(BaseSearchDto<List<ProductDto>> searchDto);

	ProductSearchDto search(ProductSearchDto searchDto);

	ProductFullDto getFullById(String id);

	ProductFullDto insert(ProductFullDto productFullDto);

	ProductFullDto update(ProductFullDto productFullDto);

	boolean isExistCode(String code);

	boolean deleteById(String id);
}

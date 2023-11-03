package com.localbrand.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductSKUDto {
	private String id;
	private String code;
	private Integer quantity;
	private ProductDto productDto;
	private List<ProductAttributeValueDto> attributeValues;
}

package com.localbrand.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductSKUFullDto {
    private String id;
    private String code;
    private Integer quantity;
    private ProductDto productDto;
    private List<ProductAttributeValueDto> attributeValues;
}

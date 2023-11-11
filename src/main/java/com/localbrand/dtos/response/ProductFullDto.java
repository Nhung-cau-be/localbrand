package com.localbrand.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductFullDto {
	private String id;
	private ProductGroupDto productGroup;
	private String code;
	private String name;
	private String mainImageUrl;
	private Integer price;
	private Integer discountPrice;
	private Integer discountPercent;
	private String describe;
	private Boolean isDeleted;
	private ProviderDto provider;

	private List<ProductImageDto> images;
	private List<ProductSKUFullDto> productSKUs;
	private List<ProductAttributeValueDto> attributeValues;
}

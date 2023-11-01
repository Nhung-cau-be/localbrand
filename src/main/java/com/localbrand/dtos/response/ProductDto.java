package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class ProductDto {
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
}

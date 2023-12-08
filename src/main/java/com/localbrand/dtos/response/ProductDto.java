package com.localbrand.dtos.response;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
	private String id;
	private ProductGroupDto productGroup;
	private CollectionDto collection;
	private String code;
	private String name;
	private String mainImageUrl;
	private Integer price;
	private Integer discountPrice;
	private Integer discountPercent;
	private String describe;
	private Boolean isDeleted;
	private ProviderDto provider;
	private Date createdDate;
	private Date updatedDate;
}

package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class ProductImageDto {
	private String id;
	private ProductDto product;
	private String url;
}

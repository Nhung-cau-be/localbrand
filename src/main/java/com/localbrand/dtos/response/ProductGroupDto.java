package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class ProductGroupDto {
	private String id;
	private CategoryDto category;
	private String name;
}

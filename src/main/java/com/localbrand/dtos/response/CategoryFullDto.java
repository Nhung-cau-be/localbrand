package com.localbrand.dtos.response;

import java.util.List;

import lombok.Data;

@Data
public class CategoryFullDto {
	
	private String id;
	
	private String code;

	private String name;
	
	private List<ProductGroupDto> productGroups;
}

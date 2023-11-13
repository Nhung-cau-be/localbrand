package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductGroupDto {
	private String id;
	
	@NotBlank(message = "Vui lòng nhập mã nhóm sản phẩm")
	private String code;
	
	private CategoryDto category;
	
	@NotBlank(message = "Vui lòng nhập tên nhóm sản phẩm")
	private String name;
}

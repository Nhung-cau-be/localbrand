package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
	private String id;
	@NotBlank(message = "Vui lòng nhập mã")
	private String code;
	@NotBlank(message = "Vui lòng nhập tên danh mục")
	private String name;
}

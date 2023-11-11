package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductAttributeValueDto {
	private String id;
	private ProductAttributeDto attribute;
	private String code;
	@NotBlank(message = "Vui lòng nhập tên giá trị thuộc tính")
	private String name;
}

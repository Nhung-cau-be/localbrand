package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProductAttributeFullDto {
	private String id;
	@NotBlank(message = "Vui lòng nhập mã thuộc tính")
	private String code;
	@NotBlank(message = "Vui lòng nhập tên thuộc tính")
	private String name;
	@Valid
	private List<ProductAttributeValueDto> values;
}

package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductAttributeValueDto {
	private String id;
	private ProductAttributeDto attribute;
	@NotBlank(message = "Vui lòng nhập mã giá trị thuộc tính")
	private String code;
	@NotBlank(message = "Vui lòng nhập tên giá trị thuộc tính")
	private String name;
	private String value;
	@NotBlank(message = "Vui lòng nhập tên số thứ tự giá trị thuộc tính")
	private Integer ordinalNumber;
}

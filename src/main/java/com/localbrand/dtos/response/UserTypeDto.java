package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserTypeDto {
	private String id;
	@NotBlank(message = "Vui lòng nhập tên loại người dùng")
	private String name;
}

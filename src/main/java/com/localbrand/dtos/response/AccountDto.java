package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AccountDto {
	private String id;
	@NotBlank (message = "Vui lòng chọn tài khoản")
	private String username;
	@NotBlank (message = "Vui lòng chọn mật khẩu")
	private String password;
	private String type;
}

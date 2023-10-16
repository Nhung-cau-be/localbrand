package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {
	private String id;
	@NotNull (message = "vui lòng nhập tài khoản")
	private String username;
	@NotNull (message = "vui lòng nhập mật khẩu")
	private String password;
	@NotNull (message = "vui lòng nhập loại tài khoản")
	private String type;
}

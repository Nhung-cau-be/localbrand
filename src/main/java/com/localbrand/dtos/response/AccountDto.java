package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDto {
	private String id;
	@NotBlank (message = "vui lòng chọn tài khoản")
	private String username;
	@NotBlank (message = "vui lòng chọn mật khẩu")
	private String password;
	private String type;
}

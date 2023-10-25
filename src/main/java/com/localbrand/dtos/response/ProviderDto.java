package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderDto {
	private String id;
	
	@NotBlank(message = "Vui lòng nhập mã nhà cung cấp")
	private String code;
	
	@NotBlank(message = "Vui lòng nhập tên nhà cung cấp")
	private String name;
	
	@NotBlank(message = "Vui lòng nhập địa chỉ nhà cung cấp")
	private String address;
}

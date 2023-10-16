package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class ProviderDto {
	private String id;
	
	@NotBlank(message = "Không được để trống code")
	private String code;
	
	@NotBlank(message = "Không được để trống name")
	private String name;
	
	@NotBlank(message = "Không được để trống address")
	private String address;
}

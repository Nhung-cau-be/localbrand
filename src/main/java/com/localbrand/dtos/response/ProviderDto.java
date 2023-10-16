package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class ProviderDto {
	@NotBlank(message = "Không được để trống id")
	private String id;
	
	@NotBlank(message = "Không được để trống code")
	private String code;
	
	@NotBlank(message = "Không được để trống name")
	private String name;
	
	@NotBlank(message = "Không được để trống address")
	private String address;
}

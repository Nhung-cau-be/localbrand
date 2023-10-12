package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class AccountDto {
	private String id;
	private String username;
	private String password;
	private String type;
}

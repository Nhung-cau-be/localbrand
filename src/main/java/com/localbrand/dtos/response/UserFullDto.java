package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFullDto {
	private String id;
	private UserTypeFullDto userType;
	private AccountDto account;
	private String name;
	private String phone;
	private Boolean isMan;
	private LocalDate birthdate;
	private String address;
	private String email;
}

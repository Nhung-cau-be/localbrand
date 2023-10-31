package com.localbrand.dtos.response;

import java.sql.Date;

import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.UserType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
	private String id;
	private UserType userType;
	@Valid
	private Account account;
	@NotBlank(message = "Vui lòng chọn tên người dùng")
	private String name;
	@NotBlank(message = "Vui lòng nhập số điện thoại người dùng")
	private String phone;
	@NotBlank(message = "Vui lòng chọn giới tính người dùng")
	private Boolean isMan;
	@NotBlank(message = "Vui lòng chọn ngày sinh người dùng")
	private Date birthdate;
	@NotBlank(message = "Vui lòng nhập địa chỉ người dùng")
	private String address;
	@NotBlank(message = "Vui lòng nhập email người dùng")
	private String email;
}

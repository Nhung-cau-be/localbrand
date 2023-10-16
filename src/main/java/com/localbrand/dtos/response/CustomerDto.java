package com.localbrand.dtos.response;

import java.util.Date;

import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.CustomerType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {
	private String id;
	@Valid
	private CustomerType customerType;
	@Valid
	private Account account;
	@NotBlank(message = "Vui lòng nhập tên khách hàng")
	private String name;
	@NotBlank(message = "Vui lòng nhập sdt khách hàng")
	private String phoneNumber;
	@NotBlank(message = "Vui lòng nhập giới tính khách hàng")
	private Boolean isMan;
	@NotBlank(message = "Vui lòng nhập ngày sinh khách hàng")
	private Date birthdate;
	@NotBlank(message = "Vui lòng nhập địa chỉ khách hàng")
	private String address;
	@NotBlank(message = "Vui lòng nhập email khách hàng")
	private String email;
	@NotBlank(message = "Vui lòng nhập điểm khách hàng thân thiết")
	private Integer membershipPoint;
	@NotBlank(message = "Vui lòng nhập tài khoản")
	private String username;
	@NotBlank(message = "Vui lòng nhập mật khẩu")
	private String password;
}

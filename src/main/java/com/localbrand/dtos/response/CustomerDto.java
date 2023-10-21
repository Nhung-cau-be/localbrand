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
	private CustomerType customerType;
	@Valid
	private Account account;
	@NotBlank(message = "Vui lòng chọn tên khách hàng")
	private String name;
	@NotBlank(message = "Vui lòng nhập sdt khách hàng")
	private String phonenumber;
	@NotBlank(message = "Vui lòng chọn giới tính khách hàng")
	private Boolean isMan;
	@NotBlank(message = "Vui lòng chọn ngày sinh khách hàng")
	private Date birthdate;
	@NotBlank(message = "Vui lòng nhập địa chỉ khách hàng")
	private String address;
	@NotBlank(message = "Vui lòng nhập email khách hàng")
	private String email;
	private Integer membershipPoint;
}

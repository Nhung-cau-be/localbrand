package com.localbrand.dtos.response;

import java.util.Date;

import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.CustomerType;


import lombok.Data;

@Data
public class CustomerDto {
	private String id;
	private CustomerType customerType;
	private Account account;
	private String name;
	private String sdt;
	private boolean isMan;
	private Date birthdate;
	private String address;
	private String email;
	private int membership_point;
	private int membership;
}

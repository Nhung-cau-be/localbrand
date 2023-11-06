package com.localbrand.dal.entity;

import com.localbrand.enums.AccountTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name = "account")
@Data
public class Account {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String username;
	@Column
	private String password;
	@Column 
	@Enumerated (EnumType.STRING)
	private AccountTypeEnum type;
}

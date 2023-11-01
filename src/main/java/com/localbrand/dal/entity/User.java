package com.localbrand.dal.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn (name = "user_type_id")
	private UserType userType;
	@OneToOne
	@JoinColumn (name = "account_id")
	private Account account;
	@Column
	private String name;
	@Column (name = "phone")
	private String phone;
	@Column (name = "is_man")
	private Boolean isMan;
	@Column
	private Date birthdate;
	@Column
	private String address;
	@Column
	private String email;
}

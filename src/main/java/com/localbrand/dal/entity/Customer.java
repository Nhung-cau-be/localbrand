package com.localbrand.dal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn (name = "customer_type_id")
	private CustomerType customerType;
	@OneToOne
	@JoinColumn (name = "account_id")
	private Account account;
	@Column
	private String name;
	@Column
	private String sdt;
	@Column
	private boolean isMan;
	@Column
	private Date birthdate;
	@Column
	private String address;
	@Column
	private String email;
	@Column
	private int membership_point;
	@Column
	private int membership;
}

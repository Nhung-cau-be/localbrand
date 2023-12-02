package com.localbrand.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "customer_message")
@Data
public class CustomerMessage {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_email")
	private String customerEmail;
	@Column
	private String message;
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;
}
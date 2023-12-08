package com.localbrand.dal.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.localbrand.enums.AccountTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name = "notification")
@Data
public class Notification {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String title;
	@Column (name = "is_read")
	private Boolean isRead;
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;
}

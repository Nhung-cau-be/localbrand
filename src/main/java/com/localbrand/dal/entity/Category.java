package com.localbrand.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {
	
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String code;
	@Column
	private String name;
}
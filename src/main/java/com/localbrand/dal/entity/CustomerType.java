package com.localbrand.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name = "customer_type")
@Data
public class CustomerType {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String name;
	@Column (name = "standard_point")
	private Integer standardPoint;
	@Column (name = "discount_percent")
	private Float discountPercent;
}

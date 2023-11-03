package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_sku")
@Data
public class ProductSKU {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String code;
	@Column
	private Integer quantity;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}

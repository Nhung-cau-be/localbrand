package com.localbrand.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_group")
@Data
public class ProductGroup {
	
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@Column
	private String code;
	@ManyToOne
	@JoinColumn (name = "category_id")
	private Category category;
	
	@Column
	private String name;
}

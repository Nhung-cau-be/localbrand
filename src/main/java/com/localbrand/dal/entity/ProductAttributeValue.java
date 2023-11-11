package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_attribute_value")
@Data
public class ProductAttributeValue {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn (name = "product_attribute")
	private ProductAttribute attribute;
	@Column
	private String code;
	@Column
	private String name;
}

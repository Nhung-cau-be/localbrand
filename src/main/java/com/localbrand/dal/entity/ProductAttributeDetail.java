package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_attribute_detail")
@Data
public class ProductAttributeDetail {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn(name = "product_sku_id")
	private ProductSKU productSKU;
	@ManyToOne
	@JoinColumn(name = "product_attribute_value_id")
	private ProductAttributeValue productAttributeValue;
}

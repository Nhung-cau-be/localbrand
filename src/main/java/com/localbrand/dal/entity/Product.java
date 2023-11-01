package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn(name = "product_group_id")
	private ProductGroup productGroup;
	@Column
	private String code;
	@Column
	private String name;
	@Column(name = "main_image_url")
	private String mainImageUrl;
	private Integer price;
	@Column(name = "discount_price")
	private Integer discountPrice;
	@Column(name = "discount_percent")
	private Integer discountPercent;
	@Column
	private String describe;
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;
}
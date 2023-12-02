package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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
	@Column(updatable = false, nullable = false)
	private String code;
	@Column
	private String name;
	@Column(name = "main_image_url", length = 1024)
	private String mainImageUrl;
	@Column
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
	@CreationTimestamp
	@Column(name = "created_date")
	private Date createdDate;
	@UpdateTimestamp
	@Column(name = "updated_date")
	private Date updatedDate;
}

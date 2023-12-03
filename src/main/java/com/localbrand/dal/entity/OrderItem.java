package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn (name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn (name = "product_sku_id")
    private ProductSKU productSKU;
    @Column
    private Integer quantity;
    @Column
    private Integer price;
    @Column(name = "discount_price")
    private Integer discountPrice;
}

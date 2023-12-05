package com.localbrand.dal.entity;

import com.localbrand.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn (name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn (name = "product_sku_id")
    private ProductSKU productSKU;
    @Column
    private Integer quantity;
}

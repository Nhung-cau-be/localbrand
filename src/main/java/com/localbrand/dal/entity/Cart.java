package com.localbrand.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    @OneToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;
}
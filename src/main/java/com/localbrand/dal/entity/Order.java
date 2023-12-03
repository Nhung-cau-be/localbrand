package com.localbrand.dal.entity;

import com.localbrand.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "order")
@Data
public class Order {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    @Column
    private String code;
    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;
    @Column (name = "customer_name")
    private String customerName;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
    @Column (name = "user_name")
    private String userName;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private Long subtotal;
    @Column
    private Long discount;
    @Column
    private Long total;
    @Column (name = "created_date")
    @CreationTimestamp
    private Date createdDate;
    @Column
    private String note;
    @Column
    @Enumerated (EnumType.STRING)
    private OrderStatusEnum status;
}

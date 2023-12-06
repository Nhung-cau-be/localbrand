package com.localbrand.dtos.response;

import com.localbrand.enums.OrderStatusEnum;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private String id;
    private String code;
    private CustomerDto customer;
    private String customerName;
    private UserDto user;
    private String userName;
    private String phone;
    private String address;
    private String email;
    private Long subtotal;
    private Long discount;
    private Long total;
    private Date createdDate;
    private String note;
    private OrderStatusEnum status;
    private Integer quantity;
}

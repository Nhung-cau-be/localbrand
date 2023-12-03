package com.localbrand.dtos.response;

import com.localbrand.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderFullDto {
    private String id;
    private String code;
    private CustomerDto customer;
    private String customerName;
    private UserDto user;
    private String userName;
    @NotNull(message = "Vui lòng nhập số điện thoại")
    private String phone;
    @NotNull(message = "Vui lòng nhập địa chỉ")
    private String address;
    @NotNull(message = "Vui lòng nhập email")
    private String email;
    private Long subtotal;
    private Long discount;
    private Long total;
    private Date createdDate;
    private String note;
    private OrderStatusEnum status;
    @NotNull (message = "Vui lòng thêm sản phẩm vào giỏ hàng")
    private List<OrderItemFullDto> items;
}

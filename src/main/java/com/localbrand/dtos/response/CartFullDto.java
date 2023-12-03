package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class CartFullDto {
    private String id;
    private CustomerDto customer;
    @Valid
    private List<CartItemFullDto> items;
    private String orderNote;
}


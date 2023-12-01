package com.localbrand.service;

import com.localbrand.dtos.response.CartItemDto;

public interface ICartItemService {
    CartItemDto insert(String customerId, CartItemDto cartItemDto);

    boolean checkCartQuantity(String customerId, CartItemDto cartItemDto);

    Boolean deleteById(String id);
}

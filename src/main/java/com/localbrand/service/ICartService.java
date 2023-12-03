package com.localbrand.service;

import com.localbrand.dtos.response.CartFullDto;
import jakarta.servlet.http.HttpServletRequest;

public interface ICartService {
    CartFullDto getByCustomerId(HttpServletRequest request, String customerId);
    CartFullDto createOrder(CartFullDto cartFullDto);

}

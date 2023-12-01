package com.localbrand.service;

import com.localbrand.dtos.response.CartDto;
import com.localbrand.dtos.response.CartFullDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ICartService {
    CartFullDto getByCustomerId(HttpServletRequest request, String customerId);
}

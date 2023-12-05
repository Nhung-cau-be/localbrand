package com.localbrand.service;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.OrderSearchDto;
import com.localbrand.dtos.request.ProductSearchDto;
import com.localbrand.dtos.response.OrderDto;
import com.localbrand.dtos.response.OrderFullDto;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getAll();

    BaseSearchDto<List<OrderDto>> findAll(BaseSearchDto<List<OrderDto>> searchDto);

    OrderSearchDto search(OrderSearchDto searchDto);

    OrderFullDto getFullById(String id);

    List<OrderDto> getOrdersByCustomerId(String customerId);

    OrderFullDto update(OrderFullDto orderFullDto);
}

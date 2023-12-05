package com.localbrand.mappers;

import com.localbrand.dal.entity.Order;
import com.localbrand.dtos.response.OrderDto;
import com.localbrand.dtos.response.OrderFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IOrderDtoMapper {
    IOrderDtoMapper INSTANCE = Mappers.getMapper(IOrderDtoMapper.class);

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtos(List<Order> orders);

    OrderFullDto toOrderFullDto(Order order);

    List<OrderFullDto> toOrderFullDtos(List<Order> orders);

    Order toOrder (OrderFullDto orderFullDto);
}

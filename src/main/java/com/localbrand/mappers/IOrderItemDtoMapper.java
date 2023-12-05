package com.localbrand.mappers;

import com.localbrand.dal.entity.OrderItem;
import com.localbrand.dtos.response.OrderItemFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IOrderItemDtoMapper {
    IOrderItemDtoMapper INSTANCE = Mappers.getMapper(IOrderItemDtoMapper.class);

    OrderItem toOrderItem(OrderItemFullDto orderItemFullDto);

    List<OrderItem> toOrderItems(List<OrderItemFullDto> orderItemFullDtos);

    OrderItemFullDto toOrderItemFullDto(OrderItem orderItem);

    List<OrderItemFullDto> toOrderItemFullDtos(List<OrderItem> orderItems);
}

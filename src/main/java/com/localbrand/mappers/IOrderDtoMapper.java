package com.localbrand.mappers;

import com.localbrand.dal.entity.Order;
import com.localbrand.dtos.response.OrderFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IOrderDtoMapper {
    IOrderDtoMapper INSTANCE = Mappers.getMapper(IOrderDtoMapper.class);

    Order toOrder (OrderFullDto orderFullDto);
}

package com.localbrand.mappers;

import com.localbrand.dal.entity.Cart;
import com.localbrand.dtos.response.CartDto;
import com.localbrand.dtos.response.CartFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICartDtoMapper {
    ICartDtoMapper INSTANCE = Mappers.getMapper(ICartDtoMapper.class);

    Cart toCart(CartFullDto cartFullDto);
    Cart toCart(CartDto cartDto);
    CartDto toCartDto(Cart cart);
    CartFullDto toCartFullDto(Cart cart);
}

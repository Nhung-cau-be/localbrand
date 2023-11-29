package com.localbrand.mappers;

import com.localbrand.dal.entity.CartItem;
import com.localbrand.dtos.response.CartItemDto;
import com.localbrand.dtos.response.CartItemFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICartItemDtoMapper {
    ICartItemDtoMapper INSTANCE = Mappers.getMapper(ICartItemDtoMapper.class);

    CartItem toCartItem(CartItemDto cartItemDto);

    CartItemFullDto toCartItemFullDto(CartItem cartItem);

    List<CartItemFullDto> toCartItemFullDtos(List<CartItem> cartItems);
}

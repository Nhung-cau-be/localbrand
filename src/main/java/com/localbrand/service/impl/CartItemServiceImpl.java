package com.localbrand.service.impl;

import com.localbrand.dal.entity.Cart;
import com.localbrand.dal.entity.CartItem;
import com.localbrand.dal.entity.Product;
import com.localbrand.dal.entity.ProductSKU;
import com.localbrand.dal.repository.ICartItemRepository;
import com.localbrand.dal.repository.ICartRepository;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.IProductSKURepository;
import com.localbrand.dtos.response.CartDto;
import com.localbrand.dtos.response.CartItemDto;
import com.localbrand.mappers.ICartDtoMapper;
import com.localbrand.mappers.ICartItemDtoMapper;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.service.ICartItemService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartItemServiceImpl implements ICartItemService {
    private static final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IProductSKURepository productSKURepository;

    @Override
    public CartItemDto insert(String customerId, CartItemDto cartItemDto) {
        try {
            CartDto cartDto = ICartDtoMapper.INSTANCE.toCartDto(cartRepository.getByCustomerId(customerId));
            if (cartDto == null)
                cartDto = createCart(customerId);

            if (isExistedProductSKU(cartItemDto.getProductSKU().getId(), cartDto.getId())) {
                cartItemRepository.updateQuantityByCartIdAndProductSKUId(cartDto.getId(), cartItemDto.getProductSKU().getId(), cartItemDto.getQuantity());
            } else {
                cartItemDto.setId(UUID.randomUUID().toString());
                cartItemDto.setCart(cartDto);
                CartItem cartItem = ICartItemDtoMapper.INSTANCE.toCartItem(cartItemDto);
                cartItemRepository.save(cartItem);
            }

            return cartItemDto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean checkCartQuantity(String customerId, CartItemDto cartItemDto) {
        Cart cart = cartRepository.getByCustomerId(customerId);
        CartItem cartItem = cartItemRepository.getByCartIdAndProductSKUId(cart.getId(),cartItemDto.getProductSKU().getId());
        return cartItem != null ? ((cartItem.getQuantity() + cartItemDto.getQuantity()) <= cartItemDto.getProductSKU().getQuantity())
                : (cartItemDto.getQuantity() <= cartItemDto.getProductSKU().getQuantity());
    }

    @Override
    @Transactional
    public Boolean deleteById(String id) {
        try {
            cartItemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace().toString());
            return false;
        }
    }


    @Transactional
    public CartDto createCart(String customerId) {
        CartDto cartDto = new CartDto();
        cartDto.setId(UUID.randomUUID().toString());
        cartDto.setCustomer(ICustomerDtoMapper.INSTANCE.toCustomerDto(customerRepository.getById(customerId)));
        cartRepository.save(ICartDtoMapper.INSTANCE.toCart(cartDto));
        return cartDto;
    }

    private boolean isExistedProductSKU(String productSKUId, String cartId) {
        return cartItemRepository.countByCartIdAndProductSKUId(cartId,productSKUId) > 0;
    }
}

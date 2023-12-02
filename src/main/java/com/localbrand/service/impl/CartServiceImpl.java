package com.localbrand.service.impl;

import com.localbrand.dal.entity.*;
import com.localbrand.dal.repository.*;
import com.localbrand.dtos.response.*;
import com.localbrand.mappers.*;
import com.localbrand.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class CartServiceImpl implements ICartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private IProductSKURepository productSKURepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProductAttributeValueRepository productAttributeValueRepository;
    @Autowired
    private IProductAttributeDetailRepository productAttributeDetailRepository;

    @Override
    public CartFullDto getByCustomerId(HttpServletRequest request, String customerId) {
        Cart cart = cartRepository.getByCustomerId(customerId);
        Customer customer = customerRepository.getById(customerId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCustomer(customer);
            cartRepository.save(newCart);

            return ICartDtoMapper.INSTANCE.toCartFullDto(newCart);
        }

        List<CartItemFullDto> cartItemDtos = ICartItemDtoMapper.INSTANCE.toCartItemFullDtos(cartItemRepository.getByCartId(cart.getId()));
        for (CartItemFullDto cartItemFullDto : cartItemDtos) {
            ProductSKU productSKU = productSKURepository.getById(cartItemFullDto.getProductSKU().getId());
            Product product = productRepository.getById(productSKU.getProduct().getId());
            List<ProductAttributeDetail> productAttributeDetails = productAttributeDetailRepository.getByProductSKUId(productSKU.getId());
            List<ProductAttributeValue> productAttributeValues = new ArrayList<>();
            for (ProductAttributeDetail productAttributeDetail : productAttributeDetails) {
                productAttributeValues.add(productAttributeValueRepository.getById(productAttributeDetail.getProductAttributeValue().getId()));
            }
            productSKU.setProduct(product);
            ProductSKUFullDto productSKUFullDto = IProductSKUDtoMapper.INSTANCE.toProductSKUFullDto(productSKU);
            productSKUFullDto.setAttributeValues(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(productAttributeValues));
            cartItemFullDto.setProductSKU(productSKUFullDto);
        }

        CartFullDto cartFullDto = ICartDtoMapper.INSTANCE.toCartFullDto(cart);
        cartFullDto.setCustomer(ICustomerDtoMapper.INSTANCE.toCustomerDto(customer));
        cartFullDto.setItems(cartItemDtos);
        return cartFullDto;
    }
}

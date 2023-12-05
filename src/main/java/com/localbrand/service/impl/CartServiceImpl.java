package com.localbrand.service.impl;

import com.localbrand.dal.entity.*;
import com.localbrand.dal.repository.*;
import com.localbrand.dtos.response.*;
import com.localbrand.enums.OrderStatusEnum;
import com.localbrand.mappers.*;
import com.localbrand.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderItemRepository orderItemRepository;

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

    @Override
    @Transactional
    public CartFullDto createOrder(CartFullDto cartFullDto) {
        try {
            if (cartFullDto == null)
                return null;

            OrderFullDto orderFullDto = new OrderFullDto();
            orderFullDto.setId(UUID.randomUUID().toString());
            orderFullDto.setCode(createOrderCode());
            orderFullDto.setCustomer(cartFullDto.getCustomer());
            orderFullDto.setCustomerName(cartFullDto.getCustomer().getName());
            orderFullDto.setPhone(cartFullDto.getCustomer().getPhone());
            orderFullDto.setAddress(cartFullDto.getCustomer().getAddress());
            orderFullDto.setEmail(cartFullDto.getCustomer().getEmail());
            orderFullDto.setSubtotal(subtotal(cartFullDto.getItems()));
            orderFullDto.setDiscount(discount(subtotal(cartFullDto.getItems()), cartFullDto.getCustomer()));
            orderFullDto.setTotal(total(subtotal(cartFullDto.getItems()), discount(subtotal(cartFullDto.getItems()), cartFullDto.getCustomer())));
            orderFullDto.setNote(cartFullDto.getOrderNote());
            orderFullDto.setStatus(OrderStatusEnum.INPROGRESS);
            orderRepository.save(IOrderDtoMapper.INSTANCE.toOrder(orderFullDto));

            saveOrderItem(cartFullDto.getItems(), orderFullDto);

            return cartFullDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    private void saveOrderItem(List<CartItemFullDto> cartItemFullDtos, OrderFullDto orderFullDto) {
        List<OrderItemFullDto> orderItemFullDtos = new ArrayList<>();
        List<String> cartItemIds = new ArrayList<>();
        for (CartItemFullDto cartItemFullDto : cartItemFullDtos) {
            OrderItemFullDto orderItemFullDto = new OrderItemFullDto();
            orderItemFullDto.setId(UUID.randomUUID().toString());
            orderItemFullDto.setOrder(orderFullDto);
            orderItemFullDto.setProductSKU(cartItemFullDto.getProductSKU());
            orderItemFullDto.setQuantity(cartItemFullDto.getQuantity());
            orderItemFullDto.setPrice(cartItemFullDto.getProductSKU().getProduct().getPrice());
            if (cartItemFullDto.getProductSKU().getProduct().getDiscountPrice() != null)
                orderItemFullDto.setDiscountPrice(cartItemFullDto.getProductSKU().getProduct().getDiscountPrice());

            orderItemFullDtos.add(orderItemFullDto);
            cartItemIds.add(cartItemFullDto.getId());
            productSKURepository.updateQuantityById(cartItemFullDto.getProductSKU().getId(), cartItemFullDto.getQuantity());
        }
        orderItemRepository.saveAll(IOrderItemDtoMapper.INSTANCE.toOrderItems(orderItemFullDtos));
        cartItemRepository.deleteByIds(cartItemIds);
    }

    private String createOrderCode() {
        int totalOrders = orderRepository.countOrders();
        String code = "HD" + (totalOrders + 1);
        return code;
    }

    private Long subtotal(List<CartItemFullDto> cartItemFullDtos) {
        long subtotal = 0;
        for(CartItemFullDto cartItemFullDto : cartItemFullDtos) {
            ProductDto productDto = cartItemFullDto.getProductSKU().getProduct();
            long price = (productDto.getDiscountPrice() != null) ? productDto.getDiscountPrice() : productDto.getPrice();
            subtotal += price*cartItemFullDto.getQuantity();
        }

        return subtotal;
    }

    private Long discount(Long subtotal, CustomerDto customerDto) {
        Float discountPercent = customerDto.getCustomerType().getDiscountPercent();
        Long discountPrice =  (long) (subtotal*discountPercent/100);
        Long roundDiscountPrice = (long) Math.round(discountPrice);

        return roundDiscountPrice;
    }

    private Long total(Long subtotal, Long discount) {
        return subtotal - discount;
    }
}

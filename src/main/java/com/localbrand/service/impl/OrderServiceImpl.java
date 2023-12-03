package com.localbrand.service.impl;

import com.localbrand.dal.entity.Order;
import com.localbrand.dal.entity.OrderItem;
import com.localbrand.dal.repository.IOrderRepository;
import com.localbrand.dtos.response.OrderFullDto;
import com.localbrand.dtos.response.OrderItemFullDto;
import com.localbrand.mappers.IOrderDtoMapper;
import com.localbrand.mappers.IOrderItemDtoMapper;
import com.localbrand.service.IOrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
}

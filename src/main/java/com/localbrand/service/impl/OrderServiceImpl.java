package com.localbrand.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbrand.dal.dao.IOrderDao;
import com.localbrand.dal.entity.Order;
import com.localbrand.dal.entity.OrderItem;
import com.localbrand.dal.entity.ProductAttributeDetail;
import com.localbrand.dal.entity.ProductAttributeValue;
import com.localbrand.dal.repository.*;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.OrderSearchDto;
import com.localbrand.dtos.response.OrderDto;
import com.localbrand.dtos.response.OrderFullDto;
import com.localbrand.mappers.*;
import com.localbrand.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IOrderItemRepository orderItemRepository;
    @Autowired
    private IProductSKURepository productSKURepository;
    @Autowired
    private IProductAttributeValueRepository productAttributeValueRepository;
    @Autowired
    private IProductAttributeDetailRepository productAttributeDetailRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<OrderDto> getAll() {
        return IOrderDtoMapper.INSTANCE.toOrderDtos(orderRepository.findAll());
    }

    @Override
    public BaseSearchDto<List<OrderDto>> findAll(BaseSearchDto<List<OrderDto>> searchDto) {
        if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("code");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Order> page = orderRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IOrderDtoMapper.INSTANCE.toOrderDtos(page.getContent()));

        return searchDto;
    }

    @Override
    public OrderSearchDto search(OrderSearchDto searchDto) {
        try {
            var oMapper = new ObjectMapper();
            Map<String, Object> map = oMapper.convertValue(searchDto, Map.class);


            var result = orderDao.search(map);
            var orderDtos = IOrderDtoMapper.INSTANCE.toOrderDtos(result.getResult());

            searchDto.setTotalRecords(result.getTotalRecords());
            searchDto.setResult(orderDtos);

            searchDto.getResult().forEach(order -> {
                List<OrderItem> orderItems = orderItemRepository.getByOrderId(order.getId());

                int count = 0;
                for (OrderItem orderItem : orderItems)
                    count += orderItem.getQuantity();

                order.setQuantity(count);
            });

            return searchDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            searchDto.setResult(null);
            searchDto.setTotalRecords(0);
            return searchDto;
        }
    }

    @Override
    public OrderFullDto getFullById(String id) {
        try {
            Order order = orderRepository.findById(id).orElse(null);

            if (order == null)
                return null;

            OrderFullDto orderFullDto = IOrderDtoMapper.INSTANCE.toOrderFullDto(order);
            orderFullDto.setItems(IOrderItemDtoMapper.INSTANCE.toOrderItemFullDtos(orderItemRepository.getByOrderId(orderFullDto.getId())));
            orderFullDto.getItems().forEach(item -> {
                item.setProductSKU(IProductSKUDtoMapper.INSTANCE.toProductSKUFullDto(productSKURepository.getById(item.getProductSKU().getId())));
                List<ProductAttributeDetail> productAttributeDetails = productAttributeDetailRepository.getByProductSKUId(item.getProductSKU().getId());
                List<ProductAttributeValue> productAttributeValues = productAttributeDetails.stream().map(ProductAttributeDetail::getProductAttributeValue).collect(Collectors.toList());
                item.getProductSKU().setAttributeValues(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(productAttributeValues));
            });

            return orderFullDto;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(String customerId) {
        List<OrderDto> orderDtos = IOrderDtoMapper.INSTANCE.toOrderDtos(orderRepository.getByCustomerId(customerId));

        if (orderDtos == null)
            return null;

        return orderDtos;
    }

    @Override
    public List<Integer> getRevenuesThisWeek(boolean isLastWeek) {
        List<Integer> revenues = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Get today's date
        LocalDate today = LocalDate.now();

        // Get Monday of current week
        LocalDate day = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        if(isLastWeek)
            day = day.minusDays(7);

        for(int i = 0; i < 7; i++) {
            day = day.plusDays(1);
            String dayString = day.format(dateTimeFormatter);
            List<Order> orders = orderRepository.getByCreatedDate(java.sql.Date.valueOf(day));

            int sum = 0;
            for(Order order : orders) {
                sum += order.getTotal();
            }

            revenues.add(sum);
        }

        return revenues;
    }

    @Override
    public List<Integer> getRevenuesByMonth() {
        List<Integer> revenues = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            List<Order> orders = orderRepository.getByCreatedMonth(i, LocalDate.now().getYear());

            int sum = 0;
            for(Order order : orders)
                sum += order.getTotal();

            revenues.add(sum);
        }
        return revenues;
    }

    @Override
    public List<OrderFullDto> getOrderFullsThisMonth() {
        List<Order> orders = orderRepository.getByCreatedMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        List<OrderFullDto> orderFullDtos = IOrderDtoMapper.INSTANCE.toOrderFullDtos(orders);

        orderFullDtos.forEach(orderFullDto ->  {
            orderFullDto.setItems(IOrderItemDtoMapper.INSTANCE.toOrderItemFullDtos(orderItemRepository.getByOrderId(orderFullDto.getId())));
            orderFullDto.getItems().forEach(item -> {
                item.setProductSKU(IProductSKUDtoMapper.INSTANCE.toProductSKUFullDto(productSKURepository.getById(item.getProductSKU().getId())));
                List<ProductAttributeDetail> productAttributeDetails = productAttributeDetailRepository.getByProductSKUId(item.getProductSKU().getId());
                List<ProductAttributeValue> productAttributeValues = productAttributeDetails.stream().map(ProductAttributeDetail::getProductAttributeValue).collect(Collectors.toList());
                item.getProductSKU().setAttributeValues(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(productAttributeValues));
            });
        });

        return orderFullDtos;
    }

    @Override
    public OrderFullDto update(OrderFullDto orderFullDto) {
        try {
            orderFullDto.setUser(IUserDtoMapper.INSTANCE.toUserDto(userRepository.getById(orderFullDto.getUser().getId())));
            orderFullDto.setUserName(orderFullDto.getUser().getName());
            Order order = IOrderDtoMapper.INSTANCE.toOrder(orderFullDto);
            orderRepository.save(order);

            return orderFullDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}

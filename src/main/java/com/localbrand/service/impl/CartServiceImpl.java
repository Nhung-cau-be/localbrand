package com.localbrand.service.impl;

import com.localbrand.dal.entity.*;
import com.localbrand.dal.repository.*;
import com.localbrand.dtos.response.*;
import com.localbrand.enums.OrderStatusEnum;
import com.localbrand.mappers.*;
import com.localbrand.service.ICartService;
import com.localbrand.service.IEmailService;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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
    @Autowired
    private IEmailService emailService;
    @Autowired
    private INotificationRepository notificationRepository;

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
            orderFullDto.setCreatedDate(new Date());
            orderFullDto.setSubtotal(subtotal(cartFullDto.getItems()));
            orderFullDto.setDiscount(discount(subtotal(cartFullDto.getItems()), cartFullDto.getCustomer()));
            orderFullDto.setTotal(total(subtotal(cartFullDto.getItems()), discount(subtotal(cartFullDto.getItems()), cartFullDto.getCustomer())));
            orderFullDto.setNote(cartFullDto.getOrderNote());
            orderFullDto.setStatus(OrderStatusEnum.INPROGRESS);
            orderRepository.save(IOrderDtoMapper.INSTANCE.toOrder(orderFullDto)); 
            Notification notification = new Notification();
            notification.setId(UUID.randomUUID().toString());
            notification.setIsRead(false);
            notification.setCreatedDate(orderFullDto.getCreatedDate());
            notification.setTitle("Đơn hàng "+ orderFullDto.getCode() + " đã được tạo");
            notificationRepository.save(notification);
            saveOrderItem(cartFullDto.getItems(), orderFullDto);

            StringBuilder htmlTable = new StringBuilder("<table>");
            htmlTable.append("<thead><tr><th>Tên sản phẩm</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th></tr></thead>");
            htmlTable.append("<tbody>");

            for (CartItemFullDto item : cartFullDto.getItems()) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(item.getProductSKU().getProduct().getName()).append("<br/>&nbsp;").append(formatAttributeValues(item.getProductSKU().getAttributeValues())).append("</td>");
                htmlTable.append("<td>").append(NumberFormat.getNumberInstance(Locale.US).format(item.getProductSKU().getProduct().getPrice())).append(" VNĐ</td>");
                htmlTable.append("<td>").append(item.getQuantity()).append("</td>");
                htmlTable.append("<td>").append(NumberFormat.getNumberInstance(Locale.US).format(item.getQuantity() * item.getProductSKU().getProduct().getPrice())).append(" VNĐ</td>");
                htmlTable.append("</tr>");
            }

            htmlTable.append("</tbody></table>");
            
            String htmlText = "<!DOCTYPE html>\r\n"
            		+ "<html lang=\"en\">\r\n"
            		+ "<head>\r\n"
            		+ "    <meta charset=\"UTF-8\">\r\n"
            		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
            		+ "    <title>Xác Nhận Đơn Hàng</title>\r\n"
            		+ "    <style>\r\n"
            		+ "        body {\r\n"
            		+ "            font-family: Arial, sans-serif;\r\n"
            		+ "            margin: 20px;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        .order-confirmation {\r\n"
            		+ "            max-width: 1000px;\r\n"
            		+ "            margin: auto;\r\n"
            		+ "            background-color: #f9f9f9;\r\n"
            		+ "            padding: 20px;\r\n"
            		+ "            border-radius: 8px;\r\n"
            		+ "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        .order-details {\r\n"
            		+ "            border-bottom: 1px solid #ddd;\r\n"
            		+ "            padding-bottom: 10px;\r\n"
            		+ "            margin-bottom: 20px;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        .order-details h2 {\r\n"
            		+ "            margin-bottom: 10px;\r\n"
            		+ "            color: #333;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        table {\r\n"
            		+ "            width: 100%;\r\n"
            		+ "            border-collapse: collapse;\r\n"
            		+ "            margin-top: 10px;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        table, th, td {\r\n"
            		+ "            border: 1px solid #ddd;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        th, td {\r\n"
            		+ "            padding: 12px;\r\n"
            		+ "            text-align: left;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        th {\r\n"
            		+ "            background-color: #f2f2f2;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        .total {\r\n"
            		+ "            margin-top: 20px;\r\n"
            		+ "            text-align: right;\r\n"
            		+ "            font-weight: bold;\r\n"
            		+ "        }\r\n"
            		+ "    </style>\r\n"
            		+ "</head>\r\n"
            		+ "<body>\r\n"
            		+ "\r\n"
            		+ "    <div class=\"order-confirmation\">\r\n"
            		+ "        <div class=\"order-details\">\r\n"
            		+ "            <h2>Xác Nhận Đơn Hàng</h2>\r\n"
            		+ "            <p><strong>Ngày xuất đơn hàng:</strong> " + orderFullDto.getCreatedDate().toString() + "</p>\r\n"
            		+ "            <p><strong>Mã đơn hàng:</strong> "+ orderFullDto.getCode() +"</p>\r\n"
            		+ "            <p><strong>Ghi chú:</strong> "+ orderFullDto.getNote() +"</p>\r\n"
            		+ "        </div>\r\n"
            		+ "\r\n"
            		+ "        <table>\r\n"
            		+ htmlTable.toString()
            		+ "        </table>\r\n"
            		+ "\r\n"
            		+ "        <div class=\"total\">\r\n"
            		+ "            <h4><strong>Tạm tính: </strong>" + NumberFormat.getNumberInstance(Locale.US).format( orderFullDto.getSubtotal()) + " VNĐ</h4>\r\n"
            		+ "            <h4><strong>Giảm giá: - </strong>" + NumberFormat.getNumberInstance(Locale.US).format( orderFullDto.getDiscount()) + " VNĐ</h4>\r\n"
            		+ "            <h4><strong>Tổng tiền: </strong>" + NumberFormat.getNumberInstance(Locale.US).format( orderFullDto.getTotal()) + " VNĐ</h4>\r\n"
            		+ "        </div>\r\n"
            		+ "    </div>\r\n"
            		+ "\r\n"
            		+ "</body>\r\n"
            		+ "</html>\r\n"
            		+ "";
            emailService.sendEmail(orderFullDto.getCustomer().getEmail(), "Xác nhận đơn hàng !", htmlText);
            
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

    private String formatAttributeValues(List<ProductAttributeValueDto> attributeValues) {
//    	List<String> attribute = attributeValues.stream().map(e -> e.getProductSKU().getProduct().getName() + ": " + e.getQuantity()).collect(Collectors.toList());
    	List<String> attribute = attributeValues.stream().map(e -> e.getAttribute().getName() + ": " + e.getName()).collect(Collectors.toList());
    	String result = String.join(", ", attribute);
        return result;
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

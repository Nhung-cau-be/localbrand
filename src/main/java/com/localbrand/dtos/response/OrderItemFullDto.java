package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemFullDto {
    private String id;
    private OrderFullDto order;
    @NotNull(message = "Vui lòng thêm sản phẩm")
    @Valid
    private ProductSKUFullDto productSKU;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;
    private Integer price;
    private Integer discountPrice;
}

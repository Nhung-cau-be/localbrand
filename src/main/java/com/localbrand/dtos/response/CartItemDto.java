package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class CartItemDto {
    private String id;
    private CartDto cart;
    @NotNull(message = "Vui lòng thêm sản phẩm")
    @Valid
    private ProductSKUDto productSKU;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;
}

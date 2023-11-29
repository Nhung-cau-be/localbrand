package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemFullDto {
    private String id;
    private CartFullDto cart;
    @NotNull(message = "Vui lòng thêm sản phẩm")
    @Valid
    private ProductSKUFullDto productSKU;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;
}

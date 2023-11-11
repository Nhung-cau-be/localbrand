package com.localbrand.dtos.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductSKUFullDto {
    @NotBlank(message = "Vui lòng thêm id của biến thể")
    private String id;
    private String code;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer quantity;
    private ProductDto productDto;
    private List<ProductAttributeValueDto> attributeValues;
}

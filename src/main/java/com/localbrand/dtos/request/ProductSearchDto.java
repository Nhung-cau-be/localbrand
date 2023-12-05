package com.localbrand.dtos.request;

import com.localbrand.dtos.response.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchDto extends BaseSearchDto<List<ProductDto>> {
    private String code;
    private String productGroupId;
    private String productId;
    private List<String> categoryIds;
    private List<String> attributeValueIds;
    private Integer minPrice;
    private Integer maxPrice;
}

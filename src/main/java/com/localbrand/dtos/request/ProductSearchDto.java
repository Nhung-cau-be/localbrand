package com.localbrand.dtos.request;

import com.localbrand.dtos.response.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchDto extends BaseSearchDto<List<ProductDto>> {
    private String code;
    private String productGroupId;
    private String productId;
    private String categoryId;
    private List<String> attributeValueIds;
    private String minPrice;
    private String maxPrice;
}

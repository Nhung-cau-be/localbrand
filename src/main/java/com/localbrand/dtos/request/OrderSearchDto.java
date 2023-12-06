package com.localbrand.dtos.request;

import com.localbrand.dtos.response.OrderDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderSearchDto extends BaseSearchDto<List<OrderDto>> {
    private String code;
    private String createdDate;
}

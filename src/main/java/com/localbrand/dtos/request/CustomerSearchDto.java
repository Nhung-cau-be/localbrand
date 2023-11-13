package com.localbrand.dtos.request;

import com.localbrand.dtos.response.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
public class CustomerSearchDto extends BaseSearchDto<List<CustomerDto>> {
    private String name;
    private String customerTypeId;
}

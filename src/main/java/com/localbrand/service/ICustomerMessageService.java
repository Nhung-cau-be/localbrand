package com.localbrand.service;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerMessageDto;

import java.util.List;


public interface ICustomerMessageService {
	List<CustomerMessageDto> getAll();

	BaseSearchDto<List<CustomerMessageDto>> findAll(BaseSearchDto<List<CustomerMessageDto>> searchDto);

	CustomerMessageDto insert(CustomerMessageDto customerMessage);
}

package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ProviderDto;

public interface ICustomerService {
	List<CustomerDto> getAll();
	
	CustomerDto getById(String id);
	
	CustomerDto insert(CustomerDto customerDto);
	
	CustomerDto update(CustomerDto customerDto);
	
	Boolean deleteById(String id);
	
	Boolean isExistPhone(String phone);
	
	Boolean isExistEmail(String email);
	
	Boolean isExistPhoneIgnore(String phone, String customerId);
	
	Boolean isExistEmailIgnore(String email, String customerId);
	
	BaseSearchDto<List<CustomerDto>> findAll(BaseSearchDto<List<CustomerDto>> searchDto);

}

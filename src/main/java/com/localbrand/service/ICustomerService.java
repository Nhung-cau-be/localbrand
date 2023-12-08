package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.CustomerSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ProviderDto;

public interface ICustomerService {
	List<CustomerDto> getAll();
	
	CustomerDto getById(String id);

	CustomerDto getByAccountId(String id);

	List<CustomerDto> getTop5Buyer();
	
	CustomerDto insert(CustomerDto customerDto);
	
	CustomerDto update(CustomerDto customerDto);
	
	CustomerDto resetPassword(String email, String newPassword);
	
	CustomerDto findByEmail(String email);
	
	boolean deleteById(String id);
	
	boolean isExistPhone(String phone);
	
	boolean isExistEmail(String email);
	
	boolean isExistPhoneIgnore(String phone, String customerId);
	
	boolean isExistEmailIgnore(String email, String customerId);
	
	BaseSearchDto<List<CustomerDto>> findAll(BaseSearchDto<List<CustomerDto>> searchDto);
	
	CustomerSearchDto search(CustomerSearchDto searchDto);
}

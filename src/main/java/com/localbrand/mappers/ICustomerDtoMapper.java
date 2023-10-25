package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dtos.response.CustomerDto;

@Mapper
public interface ICustomerDtoMapper {
	ICustomerDtoMapper INSTANCE = Mappers.getMapper(ICustomerDtoMapper.class);
	
	Customer toCustomer (CustomerDto customerDto);
	
	CustomerDto toCustomerDto (Customer customer);
	
	List<CustomerDto> toCustomerDtos(List<Customer> customers);
}

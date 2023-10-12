package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.ICustomerTypeDtoMapper;
import com.localbrand.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;

	@Override
	public List<CustomerDto> getAll() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		
		return customerDtos;
	}

	@Override
	public CustomerDto insert(CustomerDto customerDto) {
		try {
			Customer customer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			customer.setId(UUID.randomUUID().toString());
			Customer newCustomer = customerRepository.save(customer);
			CustomerDto newCustomerDto = ICustomerDtoMapper.INSTANCE.toCustomerDto(newCustomer);
			
			return newCustomerDto;
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public CustomerDto update(CustomerDto customerDto ) {
			Customer newCustomer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			String id = customerDto.getId();
			Customer customer = customerRepository.findById(id).orElse(null);
			if (customer != null) {
				customer.setCustomerType(newCustomer.getCustomerType());
				customer.setAccount(newCustomer.getAccount());
				customer.setName(newCustomer.getName());
				customer.setSdt(newCustomer.getSdt());
				customer.setMan(newCustomer.isMan());
				customer.setBirthdate(newCustomer.getBirthdate());
				customer.setAddress(newCustomer.getAddress());
				customer.setEmail(newCustomer.getEmail());
				customer.setMembership_point(newCustomer.getMembership_point());
				customer.setMembership(newCustomer.getMembership());
				Customer update = customerRepository.save(customer);
				CustomerDto newCustomerDto = ICustomerDtoMapper.INSTANCE.toCustomerDto(update);
				
				return newCustomerDto;
				}
			
			return null;
	}

	@Override
	public Boolean deleteById(String id) {
		try {
			Customer customer = customerRepository.findById(id).orElse(null);
			if (customer != null) {
				customerRepository.deleteById(id);
				
				return true;
			}
			return false;
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			
			return null;
		}
	}

	@Override
	public List<CustomerDto> searchByName(String name) {
		 List<Customer> customers = customerRepository.findByName(name);
		 List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		 
		 return customerDtos;
	}

	@Override
	public List<CustomerDto> searchBySdt(String sdt) {
		List<Customer> customers = customerRepository.findBySdt(sdt);
		 List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		 
		 return customerDtos;
	}
}

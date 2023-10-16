package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.mappers.IAccountDtoMapper;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.ICustomerTypeDtoMapper;
import com.localbrand.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IAccountRepository accountRepository;

	@Override
	public List<CustomerDto> getAll() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		
		return customerDtos;
	}
	
	@Override
	public CustomerDto getById(String id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		return ICustomerDtoMapper.INSTANCE.toCustomerDto(customer);
	}

	@Override
	public CustomerDto insert(CustomerDto customerDto) {
		try {
			Customer customer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			customer.setId(UUID.randomUUID().toString());
			
			Customer newCustomer = customerRepository.save(customer);
			
			Account account = new Account();
			account.setId(UUID.randomUUID().toString());
			account.setUsername(customer.getUsername());
			account.setPassword(customer.getPassword());
			account.setType("Khách Hàng");
			accountRepository.save(account);
			
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
				customer.setName(newCustomer.getName());
				customer.setPhoneNumber(newCustomer.getPhoneNumber());
				customer.setIsMan(newCustomer.getIsMan());
				customer.setBirthdate(newCustomer.getBirthdate());
				customer.setAddress(newCustomer.getAddress());
				customer.setEmail(newCustomer.getEmail());
				customer.setMembershipPoint(newCustomer.getMembershipPoint());
				customer.setUsername(newCustomer.getUsername());
				customer.setPassword(newCustomer.getPassword());
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
	public List<CustomerDto> searchByPhoneNumber(String phoneNumber) {
		List<Customer> customers = customerRepository.findByPhoneNumber(phoneNumber);
		 List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		 
		 return customerDtos;
	}
	
	@Override
	public Boolean isExitsPhoneNumber(String phoneNumber) {
		return customerRepository.countByPhoneNumber(phoneNumber) > 0;
	}
	
	@Override
	public Boolean isExitsEmail(String email) {
		return customerRepository.countByEmail(email) > 0;
	}
	
	@Override
	public Boolean isExitsPhoneNumberIgnore(String phoneNumber,  String customerId) {
		return customerRepository.countByPhoneNumberIgnore(phoneNumber, customerId) > 0;
	}
	
	@Override
	public Boolean isExitsEmailIgnore(String email,  String customerId) {
		return customerRepository.countByEmailIgnore(email, customerId) > 0;
	}

	@Override
	public Boolean isExitsUsername(String username) {
		return customerRepository.countByUsername(username) > 0;
	}
	
	
}

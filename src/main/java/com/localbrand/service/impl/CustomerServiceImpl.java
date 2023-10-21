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
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.mappers.IAccountDtoMapper;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;
	

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
			
			CustomerType customerType = customerTypeRepository.findByStandardPoint(0);

	        if (customerType != null) {
	            customer.setCustomerType(customerType);
	        }

			customer.setMembershipPoint(0);
			
			Account account = new Account();
		    account.setId(UUID.randomUUID().toString());
		    account.setUsername(customerDto.getAccount().getUsername());
		    account.setPassword(customerDto.getAccount().getPassword());
		    account.setType("Khách Hàng");
		   
		    customer.setAccount(account);
		    
		    accountRepository.save(account);
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
		try {
			Customer customer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			
			customerRepository.save(customer);			
			
			return customerDto;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean deleteById(String id) {
		try {
			customerRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
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


	
	
}

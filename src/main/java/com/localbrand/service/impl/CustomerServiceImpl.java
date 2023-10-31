package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.localbrand.AES;
import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.entity.Provider;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ProviderDto;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.IProviderDtoMapper;
import com.localbrand.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;
	
	final String secretKey = "locabrand!";

	@Override
	public List<CustomerDto> getAll() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customerDtos = ICustomerDtoMapper.INSTANCE.toCustomerDtos(customers);
		
		return customerDtos;
	}
	@Override
	public BaseSearchDto<List<CustomerDto>> findAll(BaseSearchDto<List<CustomerDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Customer> page = customerRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(ICustomerDtoMapper.INSTANCE.toCustomerDtos(page.getContent()));

        return searchDto;
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
		    String encryptedpassword = AES.encrypt(customerDto.getAccount().getPassword(), secretKey);  
		    account.setPassword(encryptedpassword);
		    account.setType("Khách Hàng");
		   
		    customer.setAccount(account);
		    
		    accountRepository.save(account);
		    Customer newCustomer = customerRepository.save(customer);
		    
			CustomerDto newCustomerDto = ICustomerDtoMapper.INSTANCE.toCustomerDto(newCustomer);
			
			return newCustomerDto;
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	@Override
	public CustomerDto update(CustomerDto customerDto ) {
		try {
			Customer customer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			String encryptedpassword = AES.encrypt(customerDto.getAccount().getPassword(), secretKey);
			customer.getAccount().setPassword(encryptedpassword);
			accountRepository.save(customer.getAccount());
			customerRepository.save(customer);			
			
			return customerDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	public Boolean isExistPhone(String phone) {
		return customerRepository.countByPhone(phone) > 0;
	}
	
	@Override
	public Boolean isExistEmail(String email) {
		return customerRepository.countByEmail(email) > 0;
	}
	
	@Override
	public Boolean isExistPhoneIgnore(String phone,  String customerId) {
		return customerRepository.countByPhoneIgnore(phone, customerId) > 0;
	}
	
	@Override
	public Boolean isExistEmailIgnore(String email,  String customerId) {
		return customerRepository.countByEmailIgnore(email, customerId) > 0;
	}


	
	
}

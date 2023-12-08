package com.localbrand.service.impl;

import java.util.*;

import com.localbrand.dal.entity.*;
import com.localbrand.dal.repository.IOrderRepository;
import com.localbrand.dtos.response.OrderDto;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbrand.AES;
import com.localbrand.dal.dao.ICustomerDao;
import com.localbrand.dal.dao.IProductDao;
import com.localbrand.dal.dao.impl.CustomerDao;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.CustomerSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ProviderDto;
import com.localbrand.enums.AccountTypeEnum;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.IProviderDtoMapper;
import com.localbrand.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerServiceImpl implements ICustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
    private ICustomerDao customerDao;
	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;

	final String secretKey = "localbrand";

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
	public CustomerSearchDto search(CustomerSearchDto searchDto) {
        try {
            var oMapper = new ObjectMapper();
            Map<String, Object> map = oMapper.convertValue(searchDto, Map.class);

            var result = customerDao.search(map);
            var customerDtoList = ICustomerDtoMapper.INSTANCE.toCustomerDtos(result.getResult());

            searchDto.setTotalRecords(result.getTotalRecords());
            searchDto.setResult(customerDtoList);

            return searchDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            searchDto.setResult(null);

            return searchDto;
        }
    }
	@Override
	public CustomerDto getById(String id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		return ICustomerDtoMapper.INSTANCE.toCustomerDto(customer);
	}

	@Override
	public CustomerDto getByAccountId(String id) {
		Customer customer = customerRepository.getByAccountId(id);
		return ICustomerDtoMapper.INSTANCE.toCustomerDto(customer);
	}

	@Override
	public List<CustomerDto> getTop5Buyer() {
		try {

			List<Order> orders = orderRepository.getAll();
			List<CustomerDto> customerDtos = new ArrayList<>();

			for(Order order : orders) {
				CustomerDto customerDto = customerDtos.stream().filter(customer -> customer.getId().equals(order.getCustomer().getId())).findFirst().orElse(null);
				if(customerDto == null) {
					customerDto = getById(order.getCustomer().getId());
					customerDto.setOrderQuantity(1);
					customerDtos.add(customerDto);
				} else {
					customerDto.setOrderQuantity(customerDto.getOrderQuantity() + 1);
				}
			}
			customerDtos.sort(new Comparator<CustomerDto>() {
				@Override
				public int compare(CustomerDto o1, CustomerDto o2) {
					return o1.getOrderQuantity().compareTo(o2.getOrderQuantity());
				}
			});

			return customerDtos.subList(0, Math.min(customerDtos.size(), 5));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}

	@Transactional
	@Override
	public CustomerDto insert(CustomerDto customerDto) {
		try {
			Customer customer = ICustomerDtoMapper.INSTANCE.toCustomer(customerDto);
			customer.setId(UUID.randomUUID().toString());
			
			CustomerType customerType = customerTypeRepository.findByStandardPoint(0);

	        if (customerType != null) {
	            customer.setCustomerType(customerType);
	        } else {
				customer.setCustomerType(null);
			}

			customer.setMembershipPoint(0);
			
			Account account = new Account();
		    account.setId(UUID.randomUUID().toString());
		    account.setUsername(customerDto.getAccount().getUsername());
		    String encryptedpassword = AES.encrypt(customerDto.getAccount().getPassword(), secretKey);
		    account.setPassword(encryptedpassword);
		    account.setType(AccountTypeEnum.CUSTOMER);
		   
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
			Customer existingCustomer = customerRepository.findById(customerDto.getId()).orElse(null);

	        if (existingCustomer != null) {
	            existingCustomer.setName(customerDto.getName());
	            accountRepository.save(existingCustomer.getAccount());
	            customerRepository.save(existingCustomer);

	            return customerDto;
	        } else {
	            return null;
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean deleteById(String id) {
		try {
			Customer customer = customerRepository.findById(id).orElse(null);
			Account account = customer.getAccount();
			
			customerRepository.deleteById(id);
			
			accountRepository.delete(account);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	
	@Override
	public boolean isExistPhone(String phone) {
		return customerRepository.countByPhone(phone) > 0;
	}
	
	@Override
	public boolean isExistEmail(String email) {
		return customerRepository.countByEmail(email) > 0;
	}
	
	@Override
	public boolean isExistPhoneIgnore(String phone,  String customerId) {
		return customerRepository.countByPhoneIgnore(phone, customerId) > 0;
	}
	
	@Override
	public boolean isExistEmailIgnore(String email,  String customerId) {
		return customerRepository.countByEmailIgnore(email, customerId) > 0;
	}


	
	
}

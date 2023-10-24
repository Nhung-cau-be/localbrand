package com.localbrand.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.ICustomerTypeDtoMapper;
import com.localbrand.service.ICustomerTypeService;



@Service
public class CustomerTypeServiceImpl implements ICustomerTypeService {
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Override
	public List<CustomerTypeDto> getAll() {
		List<CustomerType> customerType = customerTypeRepository.findAll();
		List<CustomerTypeDto> customerTypeDtos = ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDtos(customerType);
		
		return customerTypeDtos;
	}


	@Override
	public CustomerTypeDto findById(String id) {
		CustomerType customerType = customerTypeRepository.findById(id).orElse(null);
		return ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDto(customerType);
	}

		
	@Override
	public CustomerTypeDto insert(CustomerTypeDto customerTypeDto){
		try {
			customerTypeDto.setId(UUID.randomUUID().toString());
			CustomerType customerType = ICustomerTypeDtoMapper.INSTANCE.toCustomerType(customerTypeDto);
			
			customerTypeRepository.save(customerType);			
			
			return customerTypeDto;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public CustomerTypeDto update(CustomerTypeDto customerTypeDto) {
		try {
			CustomerType customerType = ICustomerTypeDtoMapper.INSTANCE.toCustomerType(customerTypeDto);
			
			customerTypeRepository.save(customerType);			
			
			return customerTypeDto;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean deleteById(String id) {
	    try {
	        CustomerType customerTypeDelete = customerTypeRepository.findById(id).orElse(null);

	        if (customerTypeDelete == null || !isUsing(id)) {
	            return false;
	        }

	        List<CustomerType> customerTypesLowerStandardPoint = customerTypeRepository.findByStandardPointDelete(customerTypeDelete.getStandardPoint());

	        if (!customerTypesLowerStandardPoint.isEmpty()) {
	            customerTypesLowerStandardPoint.sort(Comparator.comparing(CustomerType::getStandardPoint));

	            CustomerType newCustomerType = customerTypesLowerStandardPoint.get(customerTypesLowerStandardPoint.size() - 1);
	            List<Customer> customersToUpdate = customerRepository.findByCustomerType(id);
	            
	            customersToUpdate.forEach(customer -> {
		            customer.setCustomerType(newCustomerType);
		            customer.setMembershipPoint(newCustomerType.getStandardPoint());
	                });
	            
	            customerRepository.saveAll(customersToUpdate);
	        }

	        customerTypeRepository.deleteById(id);

	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	@Override
	public Boolean isUsing(String id) {
		return customerRepository.countByCustomerTypeId(id) > 0;
	}
	
	@Override
	public Boolean isExistName(String name) {
		return customerTypeRepository.countByName(name) > 0;
	}
	
	@Override
	public Boolean isExistNameIgnore(String name, String customerTypeId) {
		return customerTypeRepository.countByNameIgnore(name, customerTypeId) > 0;
	}
	
	@Override
	public Boolean isExistStandardPoint(Integer standardPoint) {
		return customerTypeRepository.countByStandardPoint(standardPoint) > 0;
	}
	
	@Override
	public Boolean isExistStandardPointIgnore(Integer standardPoint, String customerTypeId) {
		return customerTypeRepository.countByStandardPointIgnore(standardPoint, customerTypeId) > 0;
	}

}

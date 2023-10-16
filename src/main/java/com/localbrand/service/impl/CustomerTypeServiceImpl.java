package com.localbrand.service.impl;

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
			CustomerType customerType = customerTypeRepository.findById(id).orElse(null);
			if (customerType != null) {
				customerTypeRepository.deleteById(id);
				
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
	public boolean isUsing(String id) {
		return customerRepository.countByCustomerTypeId(id) > 0;
	}
	
	@Override
	public Boolean isExitsName(String name) {
		return customerTypeRepository.countByName(name) > 0;
	}
	
	@Override
	public Boolean isExitsNameIgnore(String name, String customerTypeId) {
		return customerTypeRepository.countByNameIgnore(name, customerTypeId) > 0;
	}
	
	@Override
	public Boolean isExitsStandardPoint(Integer standardPoint) {
		return customerTypeRepository.countByStandardPoint(standardPoint) > 0;
	}
	
	@Override
	public Boolean isExitsStandardPointIgnore(Integer standardPoint, String customerTypeId) {
		return customerTypeRepository.countByStandardPointIgnore(standardPoint, customerTypeId) > 0;
	}

}

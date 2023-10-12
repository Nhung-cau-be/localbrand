package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.mappers.ICustomerTypeDtoMapper;
import com.localbrand.service.ICustomerTypeService;



@Service
public class CustomerTypeServiceImpl implements ICustomerTypeService {
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;

	
	@Override
	public List<CustomerTypeDto> getAll() {
		List<CustomerType> customerType = customerTypeRepository.findAll();
		List<CustomerTypeDto> customerTypeDtos = ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDtos(customerType);
		
		return customerTypeDtos;
	}


	@Override
	public CustomerTypeDto insert(CustomerTypeDto customerTypeDto){
		try {
			CustomerType customerType = ICustomerTypeDtoMapper.INSTANCE.toCustomerType(customerTypeDto);
			customerType.setId(UUID.randomUUID().toString());
			CustomerType newCustomerType = customerTypeRepository.save(customerType);
			CustomerTypeDto newCustomerTypeDto = ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDto(newCustomerType);
			
			return newCustomerTypeDto;
		}
		catch (Exception e) {
			return null;
		}
	}


	@Override
	public CustomerTypeDto update(CustomerTypeDto customerTypeDto) {
		CustomerType newCustomerType = ICustomerTypeDtoMapper.INSTANCE.toCustomerType(customerTypeDto);
		String id = customerTypeDto.getId();
		CustomerType customerType = customerTypeRepository.findById(id).orElse(null);
		if(customerType != null) {		
			customerType.setName(newCustomerType.getName());
			customerType.setStandardPoint(newCustomerType.getStandardPoint());
			customerType.setDiscountPercent(newCustomerType.getDiscountPercent());
			CustomerType update = customerTypeRepository.save(customerType);
			CustomerTypeDto newCustomerTypeDto = ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDto(update);
			
			return newCustomerTypeDto;
			}
		return null;
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
	public Boolean isUsingName(String name) {
		try {
			int count = customerTypeRepository.countByName(name);
			if(count != 0)
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return null;
		}
	}


}

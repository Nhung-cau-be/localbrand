package com.localbrand.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.CustomerTypeDto;
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
	public BaseSearchDto<List<CustomerTypeDto>> findAll(BaseSearchDto<List<CustomerTypeDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<CustomerType> page = customerTypeRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(ICustomerTypeDtoMapper.INSTANCE.toCustomerTypeDtos(page.getContent()));

        return searchDto;
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
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean deleteById(String id) {
	    try {
	        CustomerType customerTypeDelete = customerTypeRepository.findById(id).orElse(null);

	        if (customerTypeDelete == null) {
	            return false;
	        }

	        if (isUsing(id)) {
	        	 List<CustomerType> customerTypesLowerStandardPoint = customerTypeRepository.findByStandardPointDelete(customerTypeDelete.getStandardPoint());

	 	        if (!customerTypesLowerStandardPoint.isEmpty()) {
	 	            customerTypesLowerStandardPoint.sort(Comparator.comparing(CustomerType::getStandardPoint));

	 	            CustomerType newCustomerType = customerTypesLowerStandardPoint.get(customerTypesLowerStandardPoint.size() - 1);
	 	            List<Customer> customersToUpdate = customerRepository.findByCustomerType(id);
	 	            
	 	            customersToUpdate.forEach(customer -> {
	 		            customer.setCustomerType(newCustomerType);	          
	 	                });
	 	            
	 	            customerRepository.saveAll(customersToUpdate);
	 	        }
	        }

	        customerTypeRepository.deleteById(id);

	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	@Override
	public boolean isUsing(String id) {
		return customerRepository.countByCustomerTypeId(id) > 0;
	}
	
	@Override
	public boolean isExistName(String name) {
		return customerTypeRepository.countByName(name) > 0;
	}
	
	@Override
	public boolean isExistNameIgnore(String name, String customerTypeId) {
		return customerTypeRepository.countByNameIgnore(name, customerTypeId) > 0;
	}
	
	@Override
	public boolean isExistStandardPoint(Integer standardPoint) {
		return customerTypeRepository.countByStandardPoint(standardPoint) > 0;
	}
	
	@Override
	public boolean isExistStandardPointIgnore(Integer standardPoint, String customerTypeId) {
		return customerTypeRepository.countByStandardPointIgnore(standardPoint, customerTypeId) > 0;
	}

}

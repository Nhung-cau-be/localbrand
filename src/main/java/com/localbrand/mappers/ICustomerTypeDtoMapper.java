package com.localbrand.mappers;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dtos.response.CustomerTypeDto;



@Mapper
public interface ICustomerTypeDtoMapper {
	ICustomerTypeDtoMapper INSTANCE = Mappers.getMapper(ICustomerTypeDtoMapper.class);
	
	CustomerTypeDto toCustomerTypeDto (CustomerType customerType);
	
	CustomerType toCustomerType (CustomerTypeDto customerTypeDto);
	
	List<CustomerTypeDto> toCustomerTypeDtos(List<CustomerType> customerType);
}

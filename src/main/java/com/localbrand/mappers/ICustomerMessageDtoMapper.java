package com.localbrand.mappers;

import com.localbrand.dal.entity.CustomerMessage;
import com.localbrand.dal.entity.UserType;
import com.localbrand.dtos.response.CustomerMessageDto;
import com.localbrand.dtos.response.UserTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ICustomerMessageDtoMapper {
	ICustomerMessageDtoMapper INSTANCE = Mappers.getMapper(ICustomerMessageDtoMapper.class);

	CustomerMessage toCustomerMessage(CustomerMessageDto customerMessageDto);

	List<CustomerMessageDto> toCustomerMessageDtos(List<CustomerMessage> customerMessages);
}

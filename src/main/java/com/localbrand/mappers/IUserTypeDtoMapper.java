package com.localbrand.mappers;
import java.util.List;

import com.localbrand.dtos.response.UserTypeFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.UserType;
import com.localbrand.dtos.response.UserTypeDto;



@Mapper
public interface IUserTypeDtoMapper {
	IUserTypeDtoMapper INSTANCE = Mappers.getMapper(IUserTypeDtoMapper.class);
	
	UserTypeDto toUserTypeDto (UserType userType);
	
	UserType toUserType (UserTypeDto userTypeDto);
	
	List<UserTypeDto> toUserTypeDtos(List<UserType> userTypes);

    UserType toUserType(UserTypeFullDto userTypeDto);

	UserTypeFullDto toUserTypeFullDto(UserType userType);
}

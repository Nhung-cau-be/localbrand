package com.localbrand.mappers;

import java.util.List;

import com.localbrand.dtos.response.UserFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.User;
import com.localbrand.dtos.response.UserDto;

@Mapper
public interface IUserDtoMapper {
	IUserDtoMapper INSTANCE = Mappers.getMapper(IUserDtoMapper.class);
	
	UserDto toUserDto (User user);
	
	User toUser (UserDto userDto);
	
	List<UserDto> toUserDtos(List<User> users);

    UserFullDto toUserFullDto(User user);
}

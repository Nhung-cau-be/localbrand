package com.localbrand.mappers;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.UserPermission;
import com.localbrand.dtos.response.UserPermissionDto;



@Mapper
public interface IUserPermissionDtoMapper {
	IUserPermissionDtoMapper INSTANCE = Mappers.getMapper(IUserPermissionDtoMapper.class);
	
	UserPermissionDto toUserPermissionDto (UserPermission userPermission);
	
	UserPermission toUserPermission (UserPermissionDto userPermissionDto);
	
	List<UserPermissionDto> toUserPermissionDtos(List<UserPermission> userPermissions);
}

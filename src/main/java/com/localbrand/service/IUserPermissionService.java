package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.UserPermissionDto;


public interface IUserPermissionService {
	List<UserPermissionDto> getAll();
	
	UserPermissionDto insert(UserPermissionDto userPermissionDto);
	
	UserPermissionDto update(UserPermissionDto userPermissionDto);
	
	boolean deleteById(String id);
}

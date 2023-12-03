package com.localbrand.service;

import java.util.List;

import com.localbrand.dal.entity.UserType;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.UserTypeDto;
import com.localbrand.dtos.response.UserTypeFullDto;


public interface IUserTypeService {
	List<UserTypeDto> getAll();
	
	UserTypeDto getById(String id);

	UserTypeFullDto getFullById(String id);
	
	BaseSearchDto<List<UserTypeDto>> findAll(BaseSearchDto<List<UserTypeDto>> searchDto);

	UserTypeFullDto insert(UserTypeFullDto userTypeDto);

	UserTypeFullDto update(UserTypeFullDto userTypeDto);
	
	boolean deleteById(String id);
	
	boolean isExistName(String name);
	
	boolean isExistNameIgnore(String name, String userTypeId);
	
	boolean isUsing(String id);
}

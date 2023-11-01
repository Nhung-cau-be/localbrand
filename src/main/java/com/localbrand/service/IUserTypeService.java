package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.UserTypeDto;


public interface IUserTypeService {
	List<UserTypeDto> getAll();
	
	UserTypeDto getById(String id);
	
	BaseSearchDto<List<UserTypeDto>> findAll(BaseSearchDto<List<UserTypeDto>> searchDto);
	
	UserTypeDto insert(UserTypeDto userTypeDto);
	
	UserTypeDto update(UserTypeDto userTypeDto);
	
	boolean deleteById(String id);
	
	boolean isExistName(String name);
	
	boolean isExistNameIgnore(String name, String userTypeId);
	
	boolean isUsing(String id);
}

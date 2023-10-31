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
	
	Boolean deleteById(String id);
	
	Boolean isExistName(String name);
	
	Boolean isExistNameIgnore(String name, String userTypeId);
	
	Boolean isUsing(String id);
}

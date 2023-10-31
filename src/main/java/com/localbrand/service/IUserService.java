package com.localbrand.service;
import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.UserDto;

public interface IUserService {
	List<UserDto> getAll();
	
	UserDto getById(String id);
	
	BaseSearchDto<List<UserDto>> findAll(BaseSearchDto<List<UserDto>> searchDto);

	UserDto insert(UserDto userDto);
	
	UserDto update(UserDto userDto);
	
	Boolean deleteById(String id);
	
	Boolean isExistPhone(String phone);
	
	Boolean isExistEmail(String email);
	
	Boolean isExistPhoneIgnore(String phone, String userId);
	
	Boolean isExistEmailIgnore(String email, String userId);
	
}

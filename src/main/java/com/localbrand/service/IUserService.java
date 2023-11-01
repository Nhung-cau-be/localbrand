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
	
	boolean deleteById(String id);
	
	boolean isExistPhone(String phone);
	
	boolean isExistEmail(String email);
	
	boolean isExistPhoneIgnore(String phone, String userId);
	
	boolean isExistEmailIgnore(String email, String userId);
	
}

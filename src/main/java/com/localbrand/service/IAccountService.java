package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CategoryDto;


public interface IAccountService {
	List<AccountDto> getAll();
	
	BaseSearchDto<List<AccountDto>> findAll(BaseSearchDto<List<AccountDto>> searchDto);

	AccountDto getById(String id);
  
	AccountDto getByUsername(String username);
	
	AccountDto update(AccountDto accountDto);

	boolean isExistUsername(String username);
	
	boolean isExistUsernameIgnore(String username, String accountId);
}

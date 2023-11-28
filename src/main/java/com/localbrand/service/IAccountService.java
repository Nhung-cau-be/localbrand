package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;


public interface IAccountService {
	List<AccountDto> getAll();
	
	boolean isUsing(String id);
	
	BaseSearchDto<List<AccountDto>> findAll(BaseSearchDto<List<AccountDto>> searchDto);

	AccountDto getById(String id);

	AccountDto insert(AccountDto accountDto);

	Boolean deleteById(String id);
	
	boolean isExitsUsername(String username);
	
	boolean isExitsUsernameIgnore(String username, String accountId);
}

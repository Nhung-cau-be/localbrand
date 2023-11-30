package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.AccountDto;


public interface IAccountService {
	List<AccountDto> getAll();
	
	AccountDto getById(String id);

	AccountDto getByUsername(String username);

	AccountDto insert(AccountDto accountDto);
	
	boolean isExitsUsername(String username);
	
	boolean isExitsUsernameIgnore(String username, String accountId);
}

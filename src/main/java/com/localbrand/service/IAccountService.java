package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.AccountDto;


public interface IAccountService {
	List<AccountDto> getAll();
	
	AccountDto insert(AccountDto accountDto);
	
	boolean deleteById(String id);
	
	boolean isExitsUsername(String username);
}

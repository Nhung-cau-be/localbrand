package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CustomerDto;


public interface IAccountService {
	List<AccountDto> getAll();
	
	AccountDto insert(AccountDto accountDto);
	
	Boolean isExitsUsername(String username);
}

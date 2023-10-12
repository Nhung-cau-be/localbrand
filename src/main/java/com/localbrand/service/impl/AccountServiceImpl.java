package com.localbrand.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.service.IAccountService;
import com.localbrand.dal.entity.Account;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.mappers.IAccountDtoMapper;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountRepository accountRepository;
	
	@Override
	public List<AccountDto> getAll() {
		List<Account> account = accountRepository.findAll();
		List<AccountDto> accountDtos = IAccountDtoMapper.INSTANCE.toAccountDtos(account);
		
		return accountDtos;
	}
	
}
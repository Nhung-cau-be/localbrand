package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.service.IAccountService;
import com.localbrand.dal.entity.Account;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.IUserRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.mappers.IAccountDtoMapper;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public List<AccountDto> getAll() {
		List<Account> account = accountRepository.findAll();
		List<AccountDto> accountDtos = IAccountDtoMapper.INSTANCE.toAccountDtos(account);
		
		return accountDtos;
	}
	@Override
	public BaseSearchDto<List<AccountDto>> findAll(BaseSearchDto<List<AccountDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("username");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Account> page = accountRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IAccountDtoMapper.INSTANCE.toAccountDtos(page.getContent()));

        return searchDto;
	}

	@Override
	public AccountDto getById(String id) {
		Account account =accountRepository.findById(id).orElse(null);
		return IAccountDtoMapper.INSTANCE.toAccountDto(account);
	}

	@Override
	public AccountDto insert(AccountDto accountDto){
		try {
			Account account = IAccountDtoMapper.INSTANCE.toAccount(accountDto);
			account.setId(UUID.randomUUID().toString());
			Account newAccount = accountRepository.save(account);
			AccountDto newAccountDto = IAccountDtoMapper.INSTANCE.toAccountDto(newAccount);
			
			return newAccountDto;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Boolean deleteById(String id) {
		try {
			
			accountRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean isUsing(String id) {
		return userRepository.countByAccountId(id) > 0;
	}
	@Override
	public boolean isExitsUsername(String username) {
		return accountRepository.countByUsername(username) > 0;
	}


	@Override
	public boolean isExitsUsernameIgnore(String username, String accountId) {
		return accountRepository.countByUsernameIgnore(username,  accountId) > 0;
	}
	
	
}
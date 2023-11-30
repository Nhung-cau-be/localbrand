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
import com.localbrand.AES;
import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.Category;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.IUserRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.mappers.IAccountDtoMapper;
import com.localbrand.mappers.ICategoryDtoMapper;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountRepository accountRepository;
	
	final String secretKey = "locabrand!";


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
	public AccountDto getByUsername(String username) {
		Account account =accountRepository.getByUsername(username);
		return IAccountDtoMapper.INSTANCE.toAccountDto(account);
	}
  
	@Override
	public AccountDto update(AccountDto accountDto) {
		try {
			Account existingAccount = accountRepository.findById(accountDto.getId()).orElse(null);
			Account updatedAccount = IAccountDtoMapper.INSTANCE.toAccount(accountDto);
	            if (!accountDto.getPassword().equals(existingAccount.getPassword())&& accountDto.getPassword() != null &&
	                    !accountDto.getPassword().isEmpty()) {
	                
	                String encryptedPassword = AES.encrypt(accountDto.getPassword(), secretKey);
	                updatedAccount.setPassword(encryptedPassword);
	            }
           accountRepository.save(updatedAccount);
           return accountDto;
           
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean isExistUsername(String username) {
		return accountRepository.countByUsername(username) > 0;
	}


	@Override
	public boolean isExistUsernameIgnore(String username, String accountId) {
		return accountRepository.countByUsernameIgnore(username,  accountId) > 0;
	}
	
	
}
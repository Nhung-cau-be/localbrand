package com.localbrand.mappers;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Account;

import com.localbrand.dtos.response.AccountDto;


@Mapper
public interface IAccountDtoMapper {
	IAccountDtoMapper INSTANCE = Mappers.getMapper(IAccountDtoMapper.class);
	
	AccountDto toAccountDto (Account account);
	
	Account toAccount (AccountDto accountDto);
	
	List<AccountDto> toAccountDtos(List<Account> accounts);
}

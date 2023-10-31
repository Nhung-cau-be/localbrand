package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.AES;
import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.User;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.IUserRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.UserDto;
import com.localbrand.mappers.ICustomerDtoMapper;
import com.localbrand.mappers.IUserDtoMapper;
import com.localbrand.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	final String secretKey = "locabrand!";
	
	
	@Override
	public List<UserDto> getAll() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = IUserDtoMapper.INSTANCE.toUserDtos(users);
		
		return userDtos;
	}
	
	@Override
	public UserDto getById(String id) {
		User user = userRepository.findById(id).orElse(null);
		return IUserDtoMapper.INSTANCE.toUserDto(user);
	}

	@Override
	public BaseSearchDto<List<UserDto>> findAll(BaseSearchDto<List<UserDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<User> page = userRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IUserDtoMapper.INSTANCE.toUserDtos(page.getContent()));

        return searchDto;
	}

	@Override
	public UserDto insert(UserDto userDto) {
		try {
			User user = IUserDtoMapper.INSTANCE.toUser(userDto);
			user.setId(UUID.randomUUID().toString());
			
			Account account = new Account();
		    account.setId(UUID.randomUUID().toString());
		    account.setUsername(userDto.getAccount().getUsername());
		    String encryptedpassword = AES.encrypt(userDto.getAccount().getPassword(), secretKey);  
		    account.setPassword(encryptedpassword);
		    account.setType("Người dùng");
		   
		    user.setAccount(account);
		    
		    accountRepository.save(account);
		    User newUser = userRepository.save(user);
		    
			UserDto newUserDto = IUserDtoMapper.INSTANCE.toUserDto(newUser);
			
			return newUserDto;
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public UserDto update(UserDto userDto) {
		try {
			User user = IUserDtoMapper.INSTANCE.toUser(userDto);
			
			String encryptedpassword = AES.encrypt(userDto.getAccount().getPassword(), secretKey);
			user.getAccount().setPassword(encryptedpassword);
			accountRepository.save(user.getAccount());
			userRepository.save(user);			
			
			return userDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean deleteById(String id) {
		try {
			userRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	

	@Override
	public Boolean isExistPhone(String phone) {
		return userRepository.countByPhone(phone) > 0;
	}
	
	@Override
	public Boolean isExistEmail(String email) {
		return userRepository.countByEmail(email) > 0;
	}
	
	@Override
	public Boolean isExistPhoneIgnore(String phone,  String customerId) {
		return userRepository.countByPhoneIgnore(phone, customerId) > 0;
	}
	
	@Override
	public Boolean isExistEmailIgnore(String email,  String customerId) {
		return userRepository.countByEmailIgnore(email, customerId) > 0;
	}

}

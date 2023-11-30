package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.repository.IUserPermissionRepository;
import com.localbrand.dal.entity.UserPermission;
import com.localbrand.dtos.response.UserPermissionDto;
import com.localbrand.mappers.IUserPermissionDtoMapper;
import com.localbrand.service.IUserPermissionService;

@Service
public class UserPermissionServiceImpl implements IUserPermissionService {

	@Autowired
	private IUserPermissionRepository userPermissionRepository;
	
	
	@Override
	public List<UserPermissionDto> getAll() {
		List<UserPermission> userPermission = userPermissionRepository.findAll();
		List<UserPermissionDto> userPermissionDtos = IUserPermissionDtoMapper.INSTANCE.toUserPermissionDtos(userPermission);
		
		return userPermissionDtos;
	}
	

	@Override
	public UserPermissionDto insert(UserPermissionDto userPermissionDto) {
		try {
			userPermissionDto.setId(UUID.randomUUID().toString());
			UserPermission userPermission = IUserPermissionDtoMapper.INSTANCE.toUserPermission(userPermissionDto);
			
			userPermissionRepository.save(userPermission);
			
			return userPermissionDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public UserPermissionDto update(UserPermissionDto userPermissionDto) {
		try {
			UserPermission userPermission = IUserPermissionDtoMapper.INSTANCE.toUserPermission(userPermissionDto);
			
			userPermissionRepository.save(userPermission);			
			
			return userPermissionDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	@Override
	public boolean deleteById(String id) {
		try {
			userPermissionRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}

package com.localbrand.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.localbrand.dtos.response.UserTypeFullDto;
import com.localbrand.mappers.IUserPermissionDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.dal.repository.IUserTypeRepository;
import com.localbrand.dal.entity.UserPermission;
import com.localbrand.dal.entity.UserType;
import com.localbrand.dal.repository.IUserPermissionRepository;
import com.localbrand.dal.repository.IUserRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.UserTypeDto;
import com.localbrand.enums.PermissionEnum;
import com.localbrand.mappers.IUserTypeDtoMapper;
import com.localbrand.service.IUserTypeService;

@Service
public class UserTypeServiceImpl implements IUserTypeService {

	@Autowired
	private IUserTypeRepository userTypeRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
    private IUserPermissionRepository userPermissionRepository;
	
	@Override
	public List<UserTypeDto> getAll() {
		List<UserType> userType = userTypeRepository.findAll();
		List<UserTypeDto> userTypeDtos = IUserTypeDtoMapper.INSTANCE.toUserTypeDtos(userType);
		
		return userTypeDtos;
	}
	
	@Override
	public UserTypeDto getById(String id) {
		UserType userType = userTypeRepository.findById(id).orElse(null);
		return IUserTypeDtoMapper.INSTANCE.toUserTypeDto(userType);
	}

	@Override
	public UserTypeFullDto getFullById(String id) {
		UserType userType = userTypeRepository.findById(id).orElse(null);
		if(userType == null)
			return null;

		UserTypeFullDto userTypeFullDto = IUserTypeDtoMapper.INSTANCE.toUserTypeFullDto(userType);
		userTypeFullDto.setPermissions(userPermissionRepository.getByUserTypeId(id).stream().map(UserPermission::getPermission).collect(Collectors.toList()));

		return userTypeFullDto;
	}

	@Override
	public BaseSearchDto<List<UserTypeDto>> findAll(BaseSearchDto<List<UserTypeDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<UserType> page = userTypeRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IUserTypeDtoMapper.INSTANCE.toUserTypeDtos(page.getContent()));

        return searchDto;
	}

	@Override
	@Transactional
	public UserTypeFullDto insert(UserTypeFullDto userTypeDto) {
		try {
			userTypeDto.setId(UUID.randomUUID().toString());
			UserType userType = IUserTypeDtoMapper.INSTANCE.toUserType(userTypeDto);
			
			userTypeRepository.save(userType);
		
			savePermissions(userTypeDto);
			
			return userTypeDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public UserTypeFullDto update(UserTypeFullDto userTypeDto) {
		try {
			UserType userType = IUserTypeDtoMapper.INSTANCE.toUserType(userTypeDto);
			
			userTypeRepository.save(userType);

			savePermissions(userTypeDto);
			
			return userTypeDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	@Override
	public boolean deleteById(String id) {
		try {
			userPermissionRepository.deleteByUserTypeId(id);
			
			userTypeRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean isExistName(String name) {
		return userTypeRepository.countByName(name) > 0;
	}
	
	@Override
	public boolean isExistNameIgnore(String name, String userTypeId) {
		return userTypeRepository.countByNameIgnore(name, userTypeId) > 0;
	}

	@Override
	public boolean isUsing(String id) {
		return userRepository.countByUserTypeId(id) > 0;
	}

	@Transactional
	private void savePermissions(UserTypeFullDto userTypeFullDto) {
		UserType userType = IUserTypeDtoMapper.INSTANCE.toUserType(userTypeFullDto);
		List<PermissionEnum> permissions = userTypeFullDto.getPermissions();
		userPermissionRepository.deleteByUserTypeId(userTypeFullDto.getId());

		List<UserPermission> userPermissions = new ArrayList<>();
		permissions.forEach(permission -> {
			UserPermission userPermission = new UserPermission();
			userPermission.setId(UUID.randomUUID().toString());
			userPermission.setUserType(userType);
			userPermission.setPermission(permission);

			userPermissions.add(userPermission);
		});
		userPermissionRepository.saveAll(userPermissions);
	}
}

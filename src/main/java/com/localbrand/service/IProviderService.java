package com.localbrand.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ProviderDto;

public interface IProviderService {
	List<ProviderDto> getAll();
	ProviderDto getById(String id);
	ProviderDto insert(ProviderDto providerDto);
	ProviderDto update(ProviderDto providerDto);
	BaseSearchDto<List<ProviderDto>> findAll(BaseSearchDto<List<ProviderDto>> searchDto);
	boolean deleteById(String id);
	boolean isExistCode(String code);
	boolean isExistCodeIgnore(String code, String providerId);
	
}

package com.localbrand.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dtos.response.ProviderDto;

public interface IProviderService {
	List<ProviderDto> getAll();
	ProviderDto getById(String id);
	ProviderDto add(ProviderDto providerDto);
	ProviderDto edit(ProviderDto providerDto);
	Boolean  deleteById(String id);
	Boolean usedCode(String code);
}

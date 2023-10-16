package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dal.repository.IProviderRepository;
import com.localbrand.dtos.response.ProviderDto;
import com.localbrand.mappers.IProviderDtoMapper;
import com.localbrand.service.IProviderService;

@Service
public class ProviderServiceImpl implements IProviderService {
	@Autowired
	private IProviderRepository providerRepository;
	
	@Override
	public List<ProviderDto> getAll() {
		List<Provider> providers = providerRepository.findAll();
		List<ProviderDto> providerDtos = IProviderDtoMapper.INSTANCE.toProviderDtos(providers);
		
		return providerDtos;
	}
	@Override
	public ProviderDto getById(String id) {
			Provider provider = providerRepository.findById(id).orElse(null);
			return IProviderDtoMapper.INSTANCE.toProviderDto(provider);
		
	}
	@Override
	public ProviderDto insert(ProviderDto providerDto){
		try {
			providerDto.setId(UUID.randomUUID().toString());
			Provider provider = IProviderDtoMapper.INSTANCE.toProvider(providerDto);
			providerRepository.save(provider);
			
			return providerDto;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	@Override
	public ProviderDto update(ProviderDto providerDto ) {
		try {
			Provider provider = IProviderDtoMapper.INSTANCE.toProvider(providerDto);
			providerRepository.save(provider);
			
			return providerDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	@Override
	public boolean deleteById(String id) {
		try {
				providerRepository.deleteById(id);
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return false;
		}
	}
	@Override
	public boolean isExistCode(String code) {
			return providerRepository.countByCode(code) > 0;
	}
	@Override
	public boolean isExistCodeIgnore(String code, String providerId) {
			return providerRepository.countByCodeIgnore(code, providerId) > 0;
	}
}

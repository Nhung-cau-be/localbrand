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
		if (provider != null) {
			ProviderDto providerDto = IProviderDtoMapper.INSTANCE.toProviderDto(provider);
			
			return providerDto;
		}
		
		return null;
	}
	@Override
	public ProviderDto insert(ProviderDto providerDto){
		try {
			Provider provider = IProviderDtoMapper.INSTANCE.toProvider(providerDto);
			provider.setId(UUID.randomUUID().toString());
			Provider create = providerRepository.save(provider);
			ProviderDto newproviderDto = IProviderDtoMapper.INSTANCE.toProviderDto(create);
			
			return newproviderDto;
		}catch (Exception e) {
			
			return null;
		}
		
	}
	@Override
	public ProviderDto update(ProviderDto providerDto ) {
		Provider newProvider = IProviderDtoMapper.INSTANCE.toProvider(providerDto);
		String id = providerDto.getId();
		Provider provider = providerRepository.findById(id).orElse(null);
		if (provider != null) {
			provider.setName(newProvider.getName());
			provider.setAddress(newProvider.getAddress());
			provider.setCode(newProvider.getCode());
			Provider create = providerRepository.save(provider);
			ProviderDto newproviderDto = IProviderDtoMapper.INSTANCE.toProviderDto(create);
			
			return newproviderDto;
        }
		
        return null;
	}
	@Override
	public Boolean deleteById(String id) {
		try {
				providerRepository.deleteById(id);
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return false;
		}
	}
	@Override
	public Boolean isUsingCode(String code) {
		try {
			return providerRepository.countByCode(code) > 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return null;
		}
	}
}

package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dtos.response.ProviderDto;

@Mapper
public interface IProviderDtoMapper {
	IProviderDtoMapper INSTANCE = Mappers.getMapper(IProviderDtoMapper.class);
	
	ProviderDto toProviderDto(Provider provider);
	Provider toProvider(ProviderDto providerDto);
	
	List<ProviderDto> toProviderDtos(List<Provider> providers);
}

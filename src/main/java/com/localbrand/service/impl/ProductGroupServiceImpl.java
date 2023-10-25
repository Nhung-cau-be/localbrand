package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.ProductGroup;
import com.localbrand.dal.repository.ICategoryRepository;
import com.localbrand.dal.repository.IProductGroupRepository;
import com.localbrand.dtos.response.ProductGroupDto;
import com.localbrand.mappers.IProductGroupDtoMapper;
import com.localbrand.service.IProductGroupService;

@Service
public class ProductGroupServiceImpl implements IProductGroupService {

	@Autowired
	private IProductGroupRepository productGroupRepository;

	@Override
	public List<ProductGroupDto> getAll() {
		List<ProductGroup> productGroups = productGroupRepository.findAll();
		List<ProductGroupDto> productGroupDtos = IProductGroupDtoMapper.INSTANCE.toProductGroupDtos(productGroups);
		return productGroupDtos;
	}

	@Override
	public ProductGroupDto getById(String id) {
		ProductGroup productGroup = productGroupRepository.findById(id).orElse(null);
		return IProductGroupDtoMapper.INSTANCE.toProductGroupDto(productGroup);
	}
	
	@Override
	public ProductGroupDto insert(ProductGroupDto productGroupDto) {
		try {
			productGroupDto.setId(UUID.randomUUID().toString());
			ProductGroup productGroup = IProductGroupDtoMapper.INSTANCE.toProductGroup(productGroupDto);
			
			productGroupRepository.save(productGroup);			
			
			return productGroupDto;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public ProductGroupDto update(ProductGroupDto productGroupDto) {
		try {
			ProductGroup productGroup = IProductGroupDtoMapper.INSTANCE.toProductGroup(productGroupDto);
			
			productGroupRepository.save(productGroup);			
			
			return productGroupDto;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean deleteById(String id) {
		try {
			productGroupRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean isExistName(String name) {
		return productGroupRepository.countByName(name) > 0;
	}
	
	@Override
	public boolean isExistNameIgnore(String name, String productGroupId) {
		return productGroupRepository.countByNameIgnore(name, productGroupId) > 0;
	}
}
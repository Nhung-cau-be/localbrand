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
		
		if (productGroup != null) {
			
			ProductGroupDto productGroupDto = IProductGroupDtoMapper.INSTANCE.toProductGroupDto(productGroup);
			
			return productGroupDto;
		}
		
		return null;
	}
	
	@Override
	public ProductGroupDto add(ProductGroupDto productGroupDto) {
		
		try {
			
			ProductGroup productGroup = IProductGroupDtoMapper.INSTANCE.toProductGroup(productGroupDto);
			
			productGroup.setId(UUID.randomUUID().toString());
			
			ProductGroup newProductGroup = productGroupRepository.save(productGroup);
			
			ProductGroupDto newProductGroupDto = IProductGroupDtoMapper.INSTANCE.toProductGroupDto(newProductGroup);
			
			return newProductGroupDto;
			
		} catch (Exception e) {
			
			return null;
		}
	}
	
	@Override
	public Boolean deleteById(String id) {
		try {
			productGroupRepository.deleteById(id);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public ProductGroupDto edit(ProductGroupDto productGroupDto) {
		
		ProductGroup newProductGroup = IProductGroupDtoMapper.INSTANCE.toProductGroup(productGroupDto);
		
		String id = productGroupDto.getId();
		
		ProductGroup productGroup = productGroupRepository.findById(id).orElse(null);
		
		if(productGroup != null) {
			
			productGroup.setName(newProductGroup.getName());
			
			productGroup.setCategory(newProductGroup.getCategory());
			
			productGroupRepository.save(productGroup);
			
			return IProductGroupDtoMapper.INSTANCE.toProductGroupDto(productGroup);
		}
		return null;
	}
	
	@Override
	public Boolean usedName(String name) {
		try {
			long count = productGroupRepository.countByName(name);
			if(count != 0)
			{
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
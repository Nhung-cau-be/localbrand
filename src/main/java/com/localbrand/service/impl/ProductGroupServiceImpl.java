package com.localbrand.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.dal.entity.ProductGroup;
import com.localbrand.dal.repository.IProductGroupRepository;
import com.localbrand.dtos.request.BaseSearchDto;
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
	public BaseSearchDto<List<ProductGroupDto>> findAll(BaseSearchDto<List<ProductGroupDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<ProductGroup> page = productGroupRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IProductGroupDtoMapper.INSTANCE.toProductGroupDtos(page.getContent()));

        return searchDto;
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
	public boolean isExistCode(String code) {
		return productGroupRepository.countByCode(code) > 0;
	}
	
	@Override
	public boolean isExistCodeIgnore(String code, String productGroupId) {
		return productGroupRepository.countByCodeIgnore(code, productGroupId) > 0;
	}
}
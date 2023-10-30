package com.localbrand.service.impl;

import com.localbrand.dal.entity.ProductAttribute;
import com.localbrand.dal.entity.ProductAttributeValue;
import com.localbrand.dal.repository.IProductAttributeRepository;
import com.localbrand.dal.repository.IProductAttributeValueRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.ProductAttributeDto;
import com.localbrand.dtos.response.ProductAttributeFullDto;
import com.localbrand.mappers.IProductAttributeDtoMapper;
import com.localbrand.mappers.IProductAttributeValueDtoMapper;
import com.localbrand.service.IProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductAttributeServiceImpl implements IProductAttributeService {
    @Autowired
    private IProductAttributeRepository productAttributeRepository;
    @Autowired
    private IProductAttributeValueRepository productAttributeValueRepository;

    @Override
    public List<ProductAttributeDto> getAll() {
        return IProductAttributeDtoMapper.INSTANCE.toProductAttributeDtos(productAttributeRepository.findAll());
    }

    @Override
    public BaseSearchDto<List<ProductAttributeDto>> findAll(BaseSearchDto<List<ProductAttributeDto>> searchDto) {
        if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("code");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<ProductAttribute> page = productAttributeRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IProductAttributeDtoMapper.INSTANCE.toProductAttributeDtos(page.getContent()));

        return searchDto;
    }

    @Override
    public ProductAttributeFullDto insert(ProductAttributeFullDto productAttributeFullDto) {
        try {
            productAttributeFullDto.setId(UUID.randomUUID().toString());
            ProductAttribute productAttribute = IProductAttributeDtoMapper.INSTANCE.toProductAttribute(productAttributeFullDto);

            productAttributeRepository.save(productAttribute);
            productAttributeFullDto = saveValues(productAttributeFullDto);

            return productAttributeFullDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isExistCode(String code) {
        return productAttributeRepository.countByCode(code) > 0;
    }

    private ProductAttributeFullDto saveValues(ProductAttributeFullDto productAttributeFullDto) {
        ProductAttribute productAttribute = IProductAttributeDtoMapper.INSTANCE.toProductAttribute(productAttributeFullDto);
        List<ProductAttributeValue> values = IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValues(productAttributeFullDto.getValues());

        values.forEach(value -> {
            value.setId(UUID.randomUUID().toString());
            value.setAttribute(productAttribute);
        });
        productAttributeValueRepository.saveAll(values);

        productAttributeFullDto.setValues(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(values));
        return productAttributeFullDto;
    }
}

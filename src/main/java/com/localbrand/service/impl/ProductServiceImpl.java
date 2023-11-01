package com.localbrand.service.impl;

import com.localbrand.dal.entity.Product;
import com.localbrand.dal.entity.ProductImage;
import com.localbrand.dal.repository.IProductImageRepository;
import com.localbrand.dal.repository.IProductRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;
import com.localbrand.mappers.IProductDtoMapper;
import com.localbrand.mappers.IProductImageDtoMapper;
import com.localbrand.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProductImageRepository productImageRepository;

    @Override
    public List<ProductDto> getAll() {
        return IProductDtoMapper.INSTANCE.toProductDtos(productRepository.findAll());
    }

    @Override
    public BaseSearchDto<List<ProductDto>> findAll(BaseSearchDto<List<ProductDto>> searchDto) {
        if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("code");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Product> page = productRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IProductDtoMapper.INSTANCE.toProductDtos(page.getContent()));

        return searchDto;
    }

    @Override
    public ProductFullDto insert(ProductFullDto productFullDto) {
        try {
            productFullDto.setId(UUID.randomUUID().toString());
            Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);

            productRepository.save(product);
            productFullDto = saveImages(productFullDto);

            return productFullDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isExistCode(String code) {
        return productRepository.countByCode(code) > 0;
    }

    private ProductFullDto saveImages(ProductFullDto productFullDto) {
        Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);
        List<ProductImage> images = IProductImageDtoMapper.INSTANCE.toProductImages(productFullDto.getImages());

        images.forEach(image -> {
            image.setId(UUID.randomUUID().toString());
            image.setProduct(product);
        });
        productImageRepository.saveAll(images);

        productFullDto.setImages(IProductImageDtoMapper.INSTANCE.toProductImageDtos(images));
        return productFullDto;
    }
}

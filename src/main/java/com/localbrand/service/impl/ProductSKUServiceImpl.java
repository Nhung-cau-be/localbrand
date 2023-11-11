package com.localbrand.service.impl;

import com.localbrand.dal.entity.ProductSKU;
import com.localbrand.dal.repository.IProductSKURepository;
import com.localbrand.dtos.response.ProductSKUFullDto;
import com.localbrand.service.IProductSKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSKUServiceImpl implements IProductSKUService {
    @Autowired
    IProductSKURepository productSKURepository;

    @Override
    public ProductSKUFullDto updateQuantity(ProductSKUFullDto productSKUFullDto) {
        ProductSKU productSKU = productSKURepository.findById(productSKUFullDto.getId()).orElse(null);
        if (productSKU == null)
            return null;

        productSKU.setQuantity(productSKUFullDto.getQuantity());
        productSKURepository.save(productSKU);

        return productSKUFullDto;
    }
}

package com.localbrand.service.impl;

import com.localbrand.dal.repository.IProductAttributeValueRepository;
import com.localbrand.service.IProductAttributeValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeValueServiceImpl implements IProductAttributeValueService {
    private static final Logger logger = LoggerFactory.getLogger(ProductAttributeValueServiceImpl.class);

    @Autowired
    private IProductAttributeValueRepository productAttributeValueRepository;

    @Override
    public boolean deleteById(String id) {
        try {
            productAttributeValueRepository.deleteById(id);

            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}

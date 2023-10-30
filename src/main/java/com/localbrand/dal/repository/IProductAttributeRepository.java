package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, String>{
    int countByCode(String code);
}

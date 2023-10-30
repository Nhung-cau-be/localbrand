package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, String>{
}

package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductAttributeValue;
import com.localbrand.dal.entity.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, String> {
    @Query("SELECT p FROM ProductAttributeValue p WHERE p.attribute.id = ?1")
    List<ProductAttributeValue> getByAttributeId(String productAttributeId);

    @Modifying
    @Query("DELETE FROM ProductAttributeValue p WHERE p.attribute.id = ?1")
    void deleteByAttributeId(String productAttributeId);
}

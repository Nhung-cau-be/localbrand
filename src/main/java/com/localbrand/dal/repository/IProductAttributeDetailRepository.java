package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductAttributeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductAttributeDetailRepository extends JpaRepository<ProductAttributeDetail, String> {
    @Query("SELECT p FROM ProductAttributeDetail p WHERE p.productSKU.product.id = ?1")
    List<ProductAttributeDetail> getByProductId(String productId);

    @Query("SELECT p FROM ProductAttributeDetail p WHERE p.productSKU.id = ?1")
    List<ProductAttributeDetail> getByProductSKUId(String productSKUId);

    @Modifying
    @Query("DELETE FROM ProductAttributeDetail p WHERE p.productSKU.product.id = ?1")
    void deleteByProductId(String id);
}

package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductSKU;
import com.localbrand.dtos.response.ProductSKUDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IProductSKURepository extends JpaRepository<ProductSKU, String> {
    @Query("SELECT p FROM ProductSKU p WHERE p.product.id = ?1")
    List<ProductSKU> getByProductId(String productId);

    @Modifying
    @Query("DELETE FROM ProductSKU p WHERE p.product.id = ?1")
    void deleteByProductId(String productId);

    @Modifying
    @Transactional
    @Query("update ProductSKU t set t.quantity = t.quantity - :quantity where t.id = :productSKUId")
    void updateQuantityById(@Param("productSKUId") String productSKUId, @Param("quantity") int quantity);
}

package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, String>{
	@Query("SELECT p FROM ProductImage p WHERE p.product.id = ?1")
    List<ProductImage> getByProductId(String productId);

    @Modifying
    @Query("DELETE FROM ProductImage p WHERE p.product.id = ?1")
    void deleteByProductId(String productId);
}

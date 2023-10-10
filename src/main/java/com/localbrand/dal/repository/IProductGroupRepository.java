package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.ProductGroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface IProductGroupRepository extends JpaRepository<ProductGroup, String>{
	
	@Query(value = "SELECT * FROM PRODUCT_GROUP WHERE CATEGORY_ID = ?1", nativeQuery = true)
	List<ProductGroup> findByCategoryId(String id);

	long countByCategoryId(String id);
	
	long countByName(String name);
}

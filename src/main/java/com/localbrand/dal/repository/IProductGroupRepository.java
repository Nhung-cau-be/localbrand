package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.ProductGroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface IProductGroupRepository extends JpaRepository<ProductGroup, String>{
	
	List<ProductGroup> findByCategoryId(String id);

	int countByCategoryId(String id);
	
	int countByName(String name);

	@Query("SELECT count(c.id) FROM ProductGroup c WHERE c.name = :name AND c.id != :productGroupId")
	int countByNameIgnore(@Param("name") String name,@Param("productGroupId") String productGroupId);
}

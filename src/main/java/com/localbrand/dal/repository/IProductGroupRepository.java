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
	
	int countByCode(String code);

	@Query("SELECT count(c.id) FROM ProductGroup c WHERE c.code = :code AND c.id != :productGroupId")
	int countByCodeIgnore(@Param("code") String code,@Param("productGroupId") String productGroupId);
}

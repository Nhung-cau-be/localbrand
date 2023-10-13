package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.ProductGroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IProductGroupRepository extends JpaRepository<ProductGroup, String>{
	
	List<ProductGroup> findByCategoryId(String id);

	int countByCategoryId(String id);
	
	int countByName(String name);
}

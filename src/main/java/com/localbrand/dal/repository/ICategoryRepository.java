package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String>{
	
	int countByCode(String code);

	@Query("SELECT count(c.id) FROM Category c WHERE c.code = :code AND c.id != :categoryId")
	int countByCodeIgnore(@Param("code") String code, @Param("categoryId") String categoryId);
}

package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.localbrand.dal.entity.Provider;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IProviderRepository extends JpaRepository<Provider, String>{
	int countByCode(String code);
	
	@Query("SELECT count(c.id) FROM Provider c WHERE c.code = :code AND c.id != :providerId")
	int countByCodeIgnore(@Param("code") String code, @Param("providerId") String providerId);
}

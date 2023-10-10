package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Provider;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IProviderRepository extends JpaRepository<Provider, String>{
	long countByCode(String code);
}

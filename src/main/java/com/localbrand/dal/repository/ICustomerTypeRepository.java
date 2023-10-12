package com.localbrand.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.CustomerType;

@Repository
public interface ICustomerTypeRepository extends JpaRepository<CustomerType, String> {
	int countByName(String name);
}

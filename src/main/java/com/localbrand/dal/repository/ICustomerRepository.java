package com.localbrand.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String>{
	@Query(value = "SELECT c FROM Customer c WHERE c.name LIKE %:name%")
	List<Customer> findByName(String name);

	@Query("SELECT c FROM Customer c WHERE c.sdt LIKE %:sdt%")
	List<Customer> findByPhoneNumber(String sdt);
}

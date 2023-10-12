package com.localbrand.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String>{
	@Query(value = "SELECT * FROM CUSTOMER WHERE NAME LIKE :name", nativeQuery = true)
	List<Customer> findByName(String name);

	@Query(value = "SELECT * FROM CUSTOMER WHERE SDT LIKE  :sdt", nativeQuery = true)
	List<Customer> findBySdt( String sdt);
}

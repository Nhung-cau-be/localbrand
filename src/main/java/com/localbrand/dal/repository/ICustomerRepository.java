package com.localbrand.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String>{
	
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.phonenumber = :phonenumber")
	int countByPhoneNumber(String phonenumber);
	
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.email = :email")
	int countByEmail(String email);
	
	@Query("SELECT COUNT(c.id) FROM Customer c WHERE c.phonenumber = :phonenumber AND c.id != :customerId")
	int countByPhoneNumberIgnore(@Param("phonenumber") String phonenumber, @Param ("customerId") String customerId);
	
	@Query("SELECT COUNT(c.id) FROM Customer c WHERE c.email = :email AND c.id != :customerId")
	int countByEmailIgnore(@Param("email") String email, @Param ("customerId") String customerId);
	
	int countByCustomerTypeId(String id);
	
	@Query("SELECT c FROM Customer c WHERE c.customerType.id = :customerTypeId")
    List<Customer> findByCustomerType(@Param("customerTypeId") String customerTypeId);
	
}

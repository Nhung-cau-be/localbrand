package com.localbrand.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String>{
	@Query(value = "SELECT c FROM Customer c WHERE c.name LIKE %:name%")
	List<Customer> findByName(String name);

	@Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
	List<Customer> findByPhoneNumber(String phoneNumber);
	
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.phoneNumber = :phoneNumber")
	int countByPhoneNumber(String phoneNumber);
	
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.email = :email")
	int countByEmail(String email);
	
	@Query("SELECT COUNT(c.id) FROM Customer c WHERE c.phoneNumber = :phoneNumber AND c.id != :customerId")
	int countByPhoneNumberIgnore(@Param("phoneNumber") String phoneNumber, @Param ("customerId") String customerId);
	
	@Query("SELECT COUNT(c.id) FROM Customer c WHERE c.email = :email AND c.id != :customerId")
	int countByEmailIgnore(@Param("email") String email, @Param ("customerId") String customerId);
	
	int countByCustomerTypeId(String id);
	
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.username = :username")
	int countByUsername(String username);
	
	@Query("SELECT COUNT(c.id) FROM Customer c WHERE c.username = :username AND c.id != :customerId")
	int countByUsernameIgnore(@Param("username") String username, @Param ("customerId") String customerId);
}

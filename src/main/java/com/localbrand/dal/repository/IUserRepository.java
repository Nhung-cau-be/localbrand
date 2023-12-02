package com.localbrand.dal.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
	@Query("SELECT u FROM User u WHERE u.account.id = ?1")
	User getByAccountId(String accountId);
	
	@Query("SELECT COUNT(c) FROM User c WHERE c.phone = :phone")
	int countByPhone(String phone);
	
	@Query("SELECT COUNT(c) FROM User c WHERE c.email = :email")
	int countByEmail(String email);
	
	@Query("SELECT COUNT(c.id) FROM User c WHERE c.phone = :phone AND c.id != :userId")
	int countByPhoneIgnore(@Param("phone") String phone, @Param ("userId") String userId);
	
	@Query("SELECT COUNT(c.id) FROM User c WHERE c.email = :email AND c.id != :userId")
	int countByEmailIgnore(@Param("email") String email, @Param ("userId") String userId);
	
	int countByUserTypeId(String id);
	
	int countByAccountId(String id);
	
	@Query("SELECT c FROM User c WHERE c.userType.id = :userTypeId")
    List<Customer> findByUserType(@Param("userTypeId") String userTypeId);
}

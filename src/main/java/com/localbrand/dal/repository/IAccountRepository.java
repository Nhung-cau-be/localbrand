package com.localbrand.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Account;



@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
	Account getByUsername(String username);

	@Query("SELECT COUNT(c) FROM Account c WHERE c.username = :username")
	int countByUsername(String username);
	
	@Query("SELECT COUNT(c.id) FROM Account c WHERE c.username = :username AND c.id != :accountId")
	int countByUsernameIgnore(@Param("username") String username, @Param ("accountId") String accountId);
}

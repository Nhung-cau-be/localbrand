package com.localbrand.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Account;



@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
	
}

package com.localbrand.dal.repository;


import com.localbrand.dal.entity.CustomerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerMessageRepository extends JpaRepository<CustomerMessage, String> {
}

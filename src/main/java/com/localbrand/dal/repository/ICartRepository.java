package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String> {
    @Query("select t from Cart t where t.customer.id = :customerId")
    Cart getByCustomerId(@Param("customerId") String customerId);
}

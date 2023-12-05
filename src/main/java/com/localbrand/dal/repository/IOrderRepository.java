package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IOrderRepository extends JpaRepository<Order, String> {
    @Query("select t from Order t where t.customer.id = :customerId")
    List<Order> getByCustomerId(@Param("customerId") String customerId);

    @Query("select count(t.id) from Order t")
    int countOrders();
}

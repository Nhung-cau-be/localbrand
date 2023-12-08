package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface IOrderRepository extends JpaRepository<Order, String> {
    @Query("select t from Order t where t.customer.id = :customerId")
    List<Order> getByCustomerId(@Param("customerId") String customerId);

    @Query("select t from Order t where DATE(t.createdDate) = ?1")
    List<Order> getByCreatedDate(Date createdDate);

    @Query("select count(t.id) from Order t")
    int countOrders();

    @Query("select t from Order t where MONTH(t.createdDate) = ?1 AND YEAR(t.createdDate) = ?2")
    List<Order> getByCreatedMonth(int month, int year);

    @Query("select t from Order t where t.status != 'CANCEL'")
    List<Order> getAll();
}

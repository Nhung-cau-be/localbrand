package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, String> {
}

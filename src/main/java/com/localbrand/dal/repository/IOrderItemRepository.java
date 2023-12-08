package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.OrderItem;
import com.localbrand.dal.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, String> {
    @Query("SELECT p FROM OrderItem p WHERE p.order.id = ?1")
    List<OrderItem> getByOrderId(String orderId);

    @Query("SELECT p FROM OrderItem p WHERE p.order.status != 'CANCEL'")
    List<OrderItem> getAllWithoutCancelOrder();
}

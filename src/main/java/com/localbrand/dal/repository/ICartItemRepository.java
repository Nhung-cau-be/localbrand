package com.localbrand.dal.repository;

import com.localbrand.dal.entity.CartItem;
import com.localbrand.dtos.response.CartItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, String> {
    @Query("select count(t.id) from CartItem t where t.cart.id = :cartId and t.productSKU.id= :productSKUId")
    int countByCartIdAndProductSKUId(@Param("cartId") String cartId, @Param("productSKUId") String productSKUId);

    @Query("select t from CartItem t where t.cart.id  = :cartId")
    List<CartItem> getByCartId(@Param("cartId") String cartId);

    @Query("select t from CartItem t where t.cart.id = :cartId and t.productSKU.id = :productSKUId")
    CartItem getByCartIdAndProductSKUId(@Param("cartId") String cartId, @Param("productSKUId") String productSKUId);

    @Modifying
    @Transactional
    @Query("update CartItem t set t.quantity = t.quantity + :quantity " +
            "where t.cart.id = :cartId and t.productSKU.id = :productSKUId")
    void updateQuantityByCartIdAndProductSKUId(@Param("cartId") String cartId, @Param("productSKUId") String productSKUId, @Param("quantity") int quantity);

    @Modifying
    @Query("delete from CartItem t where t.id in (:ids)")
    void deleteByIds(@Param("ids") List<String> ids);

    @Query("select count(t) from CartItem t where t.cart.id = :cartId")
    int countByCartId(@Param("cartId") String cartId);

}

package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, String>{
    int countByCode(String code);

    @Query("select count(t.id) from Product t where t.collection.id = :collectionId")
    int countByCollectionId(@Param("collectionId") String collectionId);

    @Query("select t from Product t where t.collection.id = :collectionId")
    List<Product> getByCollectionId(@Param("collectionId") String collectionId);
}

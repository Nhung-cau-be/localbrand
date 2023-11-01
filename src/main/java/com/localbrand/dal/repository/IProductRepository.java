package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, String>{
    int countByCode(String code);
}

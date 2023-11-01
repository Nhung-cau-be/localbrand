package com.localbrand.dal.repository;

import com.localbrand.dal.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductAttributeRepository extends JpaRepository<ProductAttribute, String>{
    int countByCode(String code);

    @Query("SELECT count(p.id) FROM ProductAttribute p WHERE p.code = ?1 AND p.id != ?2")
    int countByCodeIgnore(String code, String id);
}

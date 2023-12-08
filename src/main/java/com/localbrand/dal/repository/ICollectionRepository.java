package com.localbrand.dal.repository;

import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICollectionRepository extends JpaRepository<Collection, String> {
}

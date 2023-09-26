package com.localbrand.dal.repository;

import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String>{

}

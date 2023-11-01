package com.localbrand.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.UserType;

@Repository
public interface IUserTypeRepository extends JpaRepository<UserType, String> {
	int countByName(String name);
	
	@Query("SELECT count(c.id) FROM UserType c WHERE c.name = :name AND c.id != :userTypeId")
	int countByNameIgnore(@Param("name") String name, @Param("userTypeId") String userTypeId);
	
}

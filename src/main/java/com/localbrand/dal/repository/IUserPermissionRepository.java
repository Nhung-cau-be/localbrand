package com.localbrand.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.UserPermission;

import jakarta.transaction.Transactional;

@Repository
public interface IUserPermissionRepository extends JpaRepository<UserPermission, String> {
	@Transactional
	@Modifying
    @Query("DELETE FROM UserPermission u WHERE u.userType.id = :userTypeId")
    void deleteByUserTypeId(String userTypeId);
	
}

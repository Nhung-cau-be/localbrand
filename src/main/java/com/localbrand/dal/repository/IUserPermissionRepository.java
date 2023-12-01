package com.localbrand.dal.repository;


import com.localbrand.dal.entity.UserPermission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserPermissionRepository extends JpaRepository<UserPermission, String> {
    @Query("SELECT u FROM UserPermission u WHERE u.userType.id = ?1")
    List<UserPermission> getByUserTypeId(String userTypeId);

	@Transactional
	@Modifying
    @Query("DELETE FROM UserPermission u WHERE u.userType.id = :userTypeId")
    void deleteByUserTypeId(String userTypeId);
}

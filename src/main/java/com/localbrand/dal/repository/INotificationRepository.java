package com.localbrand.dal.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.Notification;

import jakarta.transaction.Transactional;



@Repository
public interface INotificationRepository extends JpaRepository<Notification, String> {
	@Query("select t from Notification t where t.isRead = :isRead")
	List<Notification> getByNotIsRead(@Param("isRead") Boolean isRead);
	
	@Transactional
	@Modifying
	@Query("update Notification t set t.isRead = true ")
	void setAllNotificationIsRead();
}

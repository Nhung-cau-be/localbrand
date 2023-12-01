package com.localbrand.dal.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.OTP;

@Repository
public interface IOTPRepository extends JpaRepository<OTP, String>{
	
	@Query("SELECT o FROM OTP o WHERE o.email = :email AND o.expirationTime > :currentDateTime")
	Optional<OTP> findValidOTP(@Param("email") String email, @Param("currentDateTime") LocalDateTime currentDateTime);
	
	OTP findByEmail(String email);
}

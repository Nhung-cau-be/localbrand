package com.localbrand.dal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "otp")
@Data
public class OTP {
    @Id
	@Column(updatable = false, nullable = false)
    private String id;

    @Column
    private int otpCode;

    @Column
    private String email;

    @Column
    private LocalDateTime expirationTime;
}


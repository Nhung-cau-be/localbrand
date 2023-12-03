package com.localbrand.dal.entity;

import com.localbrand.enums.PermissionEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_permission")
@Data
public class UserPermission {
	
	@Id
    @Column(name = "id")
    private String id;
	@ManyToOne
	@JoinColumn(name = "user_type_id")
    private UserType userType;
	
	@Enumerated (EnumType.STRING)
    @Column(length = 50)
    private PermissionEnum permission;

}
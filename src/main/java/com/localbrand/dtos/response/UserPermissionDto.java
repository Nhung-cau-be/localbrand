package com.localbrand.dtos.response;

import com.localbrand.dal.entity.UserType;
import com.localbrand.enums.UserPermissionEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPermissionDto {
	private String id;
	
	private UserType userType;
	
    private UserPermissionEnum permission;
}

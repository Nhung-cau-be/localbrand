package com.localbrand.dtos.response;

import com.localbrand.enums.PermissionEnum;
import lombok.Data;

@Data
public class UserPermissionDto {
	private String id;
	
	private UserTypeDto userType;
	
    private PermissionEnum permission;
}

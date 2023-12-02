package com.localbrand.dtos.response;

import com.localbrand.enums.PermissionEnum;
import lombok.Data;

import java.util.List;

@Data
public class UserTypeFullDto {
	private String id;
	private String name;
	private List<PermissionEnum> permissions;
}

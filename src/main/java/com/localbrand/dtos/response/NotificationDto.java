package com.localbrand.dtos.response;

import java.util.Date;

import lombok.Data;

@Data
public class NotificationDto {
	private String id;
	private String title;
	private Boolean isRead;
	private Date createdDate;
}

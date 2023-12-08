package com.localbrand.dtos.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class NotificationDto {
	private String id;
	private String title;
	private Boolean isRead;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Date createdDate;
}

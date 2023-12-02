package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerMessageDto {
	private String id;
	@NotBlank(message = "Vui lòng nhập tên")
	private String customerName;
	@NotBlank(message = "Vui lòng nhập email")
	private String customerEmail;
	@NotBlank(message = "Vui lòng nhập nội dung tin nhắn")
	private String message;
	private Date createdDate;
}
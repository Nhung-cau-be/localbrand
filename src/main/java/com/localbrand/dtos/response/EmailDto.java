package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
	@NotBlank (message = "Vui lòng điền email")
	private String to;
	@NotBlank (message = "Vui lòng điền tiêu đề")
	private String subject;
	@NotBlank (message = "Vui lòng điền nội dung")
	 private String text;
}

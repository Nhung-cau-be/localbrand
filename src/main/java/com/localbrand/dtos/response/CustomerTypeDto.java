package com.localbrand.dtos.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerTypeDto {
	private String id;
	@NotBlank (message = "Vui lòng nhập tên loại khách hàng")
	private String name;
	@NotBlank (message = "Vui lòng nhập điểm tiêu chuẩn")
	private Integer standardPoint;
	@NotBlank (message = "Vui lòng nhập phần trăm giảm giá")
	private Float discountPercent;
}

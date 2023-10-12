package com.localbrand.dtos.response;

import lombok.Data;

@Data
public class CustomerTypeDto {
	private String id;
	private String name;
	private int standardPoint;
	private float discountPercent;
}

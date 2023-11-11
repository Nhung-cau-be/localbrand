package com.localbrand.service;

import com.localbrand.dtos.response.ProductSKUFullDto;

public interface IProductSKUService {
	ProductSKUFullDto updateQuantity(ProductSKUFullDto productSKU);
}

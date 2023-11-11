package com.localbrand.controller;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.ProductSearchDto;
import com.localbrand.dtos.response.ProductDto;
import com.localbrand.dtos.response.ProductFullDto;
import com.localbrand.dtos.response.ProductSKUFullDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProductSKUService;
import com.localbrand.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product-sku")
public class ProductSKUController {
	@Autowired
	private IProductSKUService productSKUService;

	@PutMapping("/update-quantity")
	public ResponseEntity<?> update(@Valid @RequestBody ProductSKUFullDto productSKU) {
		ProductSKUFullDto result = productSKUService.updateQuantity(productSKU);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật số lượng biến thể sản phẩm thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật số lượng biến thể sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	}
}

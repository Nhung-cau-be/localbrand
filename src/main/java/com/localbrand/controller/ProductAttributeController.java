package com.localbrand.controller;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.ProductAttributeDto;
import com.localbrand.dtos.response.ProductAttributeFullDto;
import com.localbrand.dtos.response.ProductGroupDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product-attribute")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductAttributeController {
	@Autowired
	private IProductAttributeService productAttributeService;
	
	@GetMapping("")
    public ResponseEntity<?> getAll() {
			List<ProductAttributeDto> result = productAttributeService.getAll();
			
	        return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }

	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<ProductAttributeDto>> searchDto) {
		BaseSearchDto<List<ProductAttributeDto>> result = productAttributeService.findAll(searchDto);

		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ProductAttributeFullDto productAttribute) {
		List<String> msg = insertValidation(productAttribute);
		if (msg.size() > 0) {
			return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
		}

		ProductAttributeFullDto result = productAttributeService.insert(productAttribute);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm thuộc tính sản phẩm thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm thuộc tính sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	}

	private List<String> insertValidation(ProductAttributeFullDto productAttribute) {
		List<String> result = new ArrayList<>();

		if (productAttributeService.isExistCode(productAttribute.getName())) {
			result.add("Tên đã tồn tại");
		}

		return result;
	}
}

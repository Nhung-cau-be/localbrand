package com.localbrand.controller;

import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProductAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/product-attribute-value")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductAttributeValueController {
	@Autowired
	private IProductAttributeValueService productAttributeValueService;

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		boolean result = productAttributeValueService.deleteById(id);
		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa giá trị thuộc tính thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa giá trị thuộc tính thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	}
}

package com.localbrand.controller;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.ProductSearchDto;
import com.localbrand.dtos.response.*;
import com.localbrand.service.IProductGroupService;
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
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<ProductDto>> searchDto) {
		BaseSearchDto<List<ProductDto>> result = productService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody ProductSearchDto searchDto) {
		ProductSearchDto search = productService.search(searchDto);
		return ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), search));
	}

	@GetMapping("get-full/{id}")
	public ResponseEntity<?> getFullById( @PathVariable String id) {
		ProductFullDto result = productService.getFullById(id);

		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Sản phẩm không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
		return res;
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@Valid @RequestBody ProductFullDto productFullDto) {
		List<String> msg = insertValidation(productFullDto);
		if (msg.size() > 0) {
			return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
		}

		ProductFullDto result = productService.insert(productFullDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm sản phẩm thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	}

	private List<String> insertValidation(ProductFullDto productFullDto) {
		List<String> result = new ArrayList<>();

		if(productFullDto.getProductGroup() == null || productFullDto.getProductGroup().getId() == null || productFullDto.getProductGroup().getId().isBlank()) {
			result.add("Vui lòng chọn nhóm sản phẩm");
		}

		if(productFullDto.getProvider() == null || productFullDto.getProvider().getId() == null || productFullDto.getProvider().getId().isBlank()) {
			result.add("Vui lòng chọn nhà cung cấp");
		}

		if(productService.isExistCode(productFullDto.getCode())) {
			result.add("Mã sản phẩm đã tồn tại");
		}

		return result;
	}
}

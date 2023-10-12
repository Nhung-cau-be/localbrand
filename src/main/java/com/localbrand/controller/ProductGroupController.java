package com.localbrand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ProductGroupDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProductGroupService;

@RestController
@RequestMapping("/productgroup")
public class ProductGroupController {
	
	@Autowired
	private IProductGroupService productGroupService;
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		try {
			List<ProductGroupDto> result = productGroupService.getAll();
			
			return ResponseEntity.ok(new ResponseDto(List.of("Danh sách nhóm sản phẩm"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh sách nhóm sản phẩm"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		try {
			ProductGroupDto result = productGroupService.getById(id);
			if(result != null) {
				return ResponseEntity.ok(new ResponseDto(List.of("Nhóm sản phẩm có ID là " + id), HttpStatus.OK.value(), result));
			}
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy nhóm sản phẩm có ID là: " + id), HttpStatus.BAD_REQUEST.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy nhóm sản phẩm "), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ProductGroupDto productGroupDto){
		try {
			if (productGroupService.isUsingName(productGroupDto.getName())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên nhóm sản phẩm đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (productGroupDto.getName().isBlank()) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên nhóm sản phẩm không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (productGroupDto.getCategory() == null)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Danh mục không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			ProductGroupDto result = productGroupService.insert(productGroupDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm nhóm sản phẩm thành công"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		try {
			boolean result = productGroupService.deleteById(id);
			
			if(result == true)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Xóa nhóm sản phẩm thành công"), HttpStatus.OK.value(), result));
			}
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductGroupDto productGroupDto) {
		try {
			if (productGroupService.isUsingName(productGroupDto.getName())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên nhóm sản phẩm đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			ProductGroupDto result = productGroupService.edit(productGroupDto);
			
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa nhóm sản phẩm thành công"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
}

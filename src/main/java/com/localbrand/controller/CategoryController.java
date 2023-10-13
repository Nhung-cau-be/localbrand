package com.localbrand.controller;

import java.util.Arrays;
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
import com.localbrand.dtos.response.CategoryFullDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<?> getAll() {
		try {
			List<CategoryDto> result = categoryService.getAll();
			return ResponseEntity.ok(new ResponseDto(List.of("Danh sách danh mục"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh sách danh mục"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		try {
			CategoryDto result = categoryService.getById(id);
			if (result != null) {
				return ResponseEntity.ok(new ResponseDto(List.of("Danh mục có ID là: " + id), HttpStatus.OK.value(), result));
			}
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh mục có ID là: " + id), HttpStatus.BAD_REQUEST.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh mục "), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@GetMapping("/get-full/{id}")
	public ResponseEntity<?> getFullById(@PathVariable String id) {
		try {
			CategoryFullDto result = categoryService.getFull(id);
			return ResponseEntity.ok(new ResponseDto(List.of("Danh mục có ID là " + id), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh mục " + id), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody CategoryDto categoryDto) {
		try {
			if (categoryService.isUsingName(categoryDto.getName())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên danh mục đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			CategoryDto result = categoryService.insert(categoryDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm danh mục thành công"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto) {
		try {
			if (categoryService.isUsingName(categoryDto.getName())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên danh mục đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			CategoryDto result = categoryService.update(categoryDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa danh mục thành công"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		try {
			if(categoryService.isUsing(id))
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Danh mục đang được sử dụng"), HttpStatus.OK.value(), null));
			}
			else {
				categoryService.deleteById(id);
				return ResponseEntity.ok(new ResponseDto(List.of("Xóa danh mục thành công"), HttpStatus.OK.value(), null));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
}
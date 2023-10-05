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
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dal.entity.Category;
import com.localbrand.dtos.response.CategoryDto;
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
			return ResponseEntity.ok(new ResponseDto(List.of("Danh sách danh mục"), 200, result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh sách danh mục"), 400, null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		
		try {
			CategoryDto result = categoryService.getById(id);
			System.out.println(result);
			return ResponseEntity.ok(new ResponseDto(List.of("Danh mục có ID là " + id), 200, result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy danh mục " + id), 500, null));
		}
		
	}

	@PostMapping("")
	public ResponseEntity<?> add(@RequestBody CategoryDto categoryDto) {

		try {
			CategoryDto result = categoryService.add(categoryDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm danh mục thành công"), 200, result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm danh mục thất bại"), 400, null));
		}
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {

		try {
			categoryService.deleteById(id);
			List<CategoryDto> result = categoryService.getAll();
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa danh mục thành công"), 200, result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa danh mục thất bại"), 400, null));
		}
	}

	@PutMapping("")
	public ResponseEntity<?> editById(@RequestBody CategoryDto categoryDto) {
		
		try {
			CategoryDto result = categoryService.editById(categoryDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa danh mục thành công"), 200, result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa danh mục thất bại"), 400, null));
		}
	}
}
package com.localbrand.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CategoryFullDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<?> getAll() {
		List<CategoryDto> result = categoryService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<CategoryDto>> searchDto) {
		BaseSearchDto<List<CategoryDto>> result = categoryService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		CategoryDto result = categoryService.getById(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Danh mục không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}
	
	@GetMapping("/get-full/{id}")
	public ResponseEntity<?> getFullById(@PathVariable String id) {
		CategoryFullDto result = categoryService.getFull(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Danh mục không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@Valid @RequestBody CategoryDto categoryDto) {
        List<String> msg = insertValidation(categoryDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		CategoryDto result = categoryService.insert(categoryDto);		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm danh mục thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
		return res;
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto) {
        List<String> msg = updateValidation(categoryDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		CategoryDto result = categoryService.update(categoryDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật danh mục thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
		return res;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
        List<String> msg = deleteValidation(id);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		boolean result = categoryService.deleteById(id);
		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa danh mục thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	
	}
	
    private List<String> insertValidation(CategoryDto categoryDto) {
        List<String> result = new ArrayList<>();

        if (categoryService.isExistCode(categoryDto.getCode())) {
            result.add("Mã đã tồn tại");
        }

        return result;
    }
	
    private List<String> updateValidation(CategoryDto categoryDto) {
        List<String> result = new ArrayList<>();
        
        if (categoryDto.getId().isBlank()) {
            result.add("Vui lòng thêm id danh mục");
            return result;
        }

        if (categoryService.isExistCodeIgnore(categoryDto.getCode(), categoryDto.getId())) {
            result.add("Mã đã tồn tại");
        }

        return result;
    }
	
    private List<String> deleteValidation(String categoryId) {
        List<String> result = new ArrayList<>();

        if (categoryService.isUsing(categoryId)) {
            result.add("Danh mục đang được sử dụng");
        }

        return result;
    }
}
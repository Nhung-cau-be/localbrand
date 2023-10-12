package com.localbrand.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("")
    public ResponseEntity<?> test() {
		List<CategoryDto> result = categoryService.getAll();
        return ResponseEntity.ok(new ResponseDto(List.of(""), 0, result));
    }
}

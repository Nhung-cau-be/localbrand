package com.localbrand.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private IAccountService accountService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<AccountDto> result = accountService.getAll();
		return ResponseEntity.ok( new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		AccountDto result = accountService.getById(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Tài khoản không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}
}

	
	
	
package com.localbrand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private IAccountService accountService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		try {
			List<AccountDto> result = accountService.getAll();
	        return ResponseEntity.ok(new ResponseDto(List.of("Danh sách Account "), HttpStatus.OK.value(), result));
		}
		catch (Exception e) {
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy Account "),HttpStatus.BAD_REQUEST.value(), null));
		}
    }
	
}

	
	
	
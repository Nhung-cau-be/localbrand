package com.localbrand.controller;

import java.util.ArrayList;
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

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IAccountService;

import jakarta.validation.Valid;

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
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<AccountDto>> searchDto) {
		BaseSearchDto<List<AccountDto>> result = accountService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		AccountDto result = accountService.getById(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Tài khoản không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody AccountDto accountDto) {
        List<String> msg = updateValidation(accountDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
        AccountDto result = accountService.update(accountDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật tài khoản thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật tài khoản thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
		return res;
	}
	private List<String> updateValidation(AccountDto accountDto) {
        List<String> result = new ArrayList<>();
        
        if (accountService.isExistUsernameIgnore(accountDto.getUsername(), accountDto.getId())) {
            result.add("Tên tài khoản đã tồn tại");
        }

        return result;
    }
	
}

	
	
	
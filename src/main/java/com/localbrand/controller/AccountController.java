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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
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
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@Valid @RequestBody AccountDto accountDto) {
        List<String> msg = insertValidation(accountDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		AccountDto result = accountService.insert(accountDto);		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm tài khoản thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm tài khoản thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
		return res;
	}
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
        List<String> msg = deleteValidation(id);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		boolean result = accountService.deleteById(id);
		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa tài khoản thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa tài khoản thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	
	}
	private List<String> insertValidation(AccountDto accountDto) {
        List<String> result = new ArrayList<>();

        if (accountService.isExitsUsername(accountDto.getUsername())) {
            result.add("Tên đăng nhập đã tồn tại");
        }

        return result;
    }
	private List<String> deleteValidation(String accountId) {
        List<String> result = new ArrayList<>();

        if (accountService.isUsing(accountId)) {
            result.add("Tài khoản đang được sử dụng");
        }

        return result;
    }
}

	
	
	
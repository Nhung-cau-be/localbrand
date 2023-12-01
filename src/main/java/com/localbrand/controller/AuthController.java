package com.localbrand.controller;

import com.localbrand.AES;
import com.localbrand.dtos.response.*;
import com.localbrand.enums.AccountTypeEnum;
import com.localbrand.service.IAccountService;
import com.localbrand.service.ICustomerService;
import com.localbrand.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final String secretKey = "localbrand";
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICustomerService customerService;

	@PostMapping("/login-admin")
	public ResponseEntity<?> loginAdmin(@Valid @RequestBody LoginDto user) {
		AccountDto accountDto = accountService.getByUsername(user.getUsername());

		if (accountDto == null || !user.getPassword().equals(AES.decrypt(accountDto.getPassword()))) {
			return ResponseEntity.badRequest().body(new ResponseDto(
					List.of("Tên đăng nhập hoặc password không đúng"),
					HttpStatus.BAD_GATEWAY.value(),
					""
			));
		}

		if (accountDto.getType() != AccountTypeEnum.USER) {
			return ResponseEntity.badRequest().body(new ResponseDto(
					List.of("Loại tài khoản không hợp lệ"),
					HttpStatus.BAD_GATEWAY.value(),
					""
			));
		}

		UserDto userDto = userService.getByAccountId(accountDto.getId());

		return ResponseEntity.ok(new ResponseDto(
				List.of("Đăng nhập thành công"),
				HttpStatus.OK.value(),
				userDto.getId()
		));
	}


	@PostMapping("/login-web")
	public ResponseEntity<?> loginWeb(@Valid @RequestBody LoginDto user) {
		AccountDto accountDto = accountService.getByUsername(user.getUsername());

		if (accountDto == null || !user.getPassword().equals(AES.decrypt(accountDto.getPassword()))) {
			return ResponseEntity.badRequest().body(new ResponseDto(
					List.of("Tên đăng nhập hoặc password không đúng"),
					HttpStatus.BAD_GATEWAY.value(),
					""
			));
		}

		if (accountDto.getType() != AccountTypeEnum.CUSTOMER) {
			return ResponseEntity.badRequest().body(new ResponseDto(
					List.of("Loại tài khoản không hợp lệ"),
					HttpStatus.BAD_GATEWAY.value(),
					""
			));
		}

		CustomerDto customerDto = customerService.getByAccountId(accountDto.getId());

		return ResponseEntity.ok(new ResponseDto(
				List.of("Đăng nhập thành công"),
				HttpStatus.OK.value(),
				customerDto.getId()
		));
	}
}

	
	
	
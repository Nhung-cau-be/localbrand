package com.localbrand.controller;

import com.localbrand.AES;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.LoginDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IAccountService;
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
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto user) {

		AccountDto userDto = accountService.getByUsername(user.getUsername());

		if (userDto == null || !user.getPassword().equals(AES.decrypt(userDto.getPassword()))) {
			return ResponseEntity.badRequest().body(new ResponseDto(
					List.of("Tên đăng nhập hoặc password không đúng"),
					HttpStatus.BAD_GATEWAY.value(),
					""
			));
		}


		return ResponseEntity.ok(new ResponseDto(
				List.of("Đăng nhập thành công"),
				HttpStatus.OK.value(),
				userDto.getId()
		));
	}

}

	
	
	
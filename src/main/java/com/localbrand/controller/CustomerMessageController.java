package com.localbrand.controller;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CustomerMessageDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.dtos.response.UserTypeDto;
import com.localbrand.service.ICustomerMessageService;
import com.localbrand.service.IUserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/customer-message")
public class CustomerMessageController {
	@Autowired
	private ICustomerMessageService customerMessageService;
	
	@PostMapping("find-all")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<CustomerMessageDto>> searchDto) {
		BaseSearchDto<List<CustomerMessageDto>> result = customerMessageService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody CustomerMessageDto customerMessageDto) {
		CustomerMessageDto result = customerMessageService.insert(customerMessageDto);

		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm tin nhắn thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm tin nhắn thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
	}
}

	
	
	
	
	

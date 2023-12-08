package com.localbrand.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.NotificationDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.INotificationService;


@RestController
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	private INotificationService notificationService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<NotificationDto> result = notificationService.getAll();
		return ResponseEntity.ok( new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@GetMapping("/get-full")
	public ResponseEntity<?> getAllNotIsRead(@RequestParam Boolean isRead) {
		List<NotificationDto> result = notificationService.getAllNotIsRead(isRead);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thành công"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<NotificationDto>> searchDto) {
		BaseSearchDto<List<NotificationDto>> result = notificationService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@GetMapping("/update")
	public ResponseEntity<?> setAllNotificationIsRead() {
		boolean result = notificationService.setAllNotificationIsRead();			

		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList(""), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}
}

	
	
	
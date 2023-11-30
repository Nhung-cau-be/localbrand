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

import com.localbrand.dtos.response.UserPermissionDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IUserPermissionService;


@RestController
@RequestMapping("/user-permission")
public class UserPermissionController {
	@Autowired
	private IUserPermissionService userPermissionService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<UserPermissionDto> result = userPermissionService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody UserPermissionDto userPermissionDto) {
		UserPermissionDto result = userPermissionService.insert(userPermissionDto);		
 		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm quyền người dùng thành công"), HttpStatus.OK.value(), result))
                 : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm quyền người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
 		return res;
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserPermissionDto userPermissionDto) {
		UserPermissionDto result = userPermissionService.update(userPermissionDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật quyền người dùng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật quyền người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
		return res;
    }
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		boolean result = userPermissionService.deleteById(id);
		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa quyền người dùng thành công"), HttpStatus.OK.value(), result))
	        : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa quyền người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
    }

}

	
	
	
	
	

package com.localbrand.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.localbrand.dtos.response.*;
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

import com.localbrand.Validate;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.service.IAccountService;
import com.localbrand.service.IUserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired 
	private IAccountService accountService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<UserDto> result = userService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		UserDto result = userService.getById(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Người dùng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}

	@GetMapping("/get-full/{id}")
	public ResponseEntity<?> getFullById(@PathVariable String id) {
		UserFullDto result = userService.getFullById(id);

		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Người dùng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
		return res;
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<UserDto>> searchDto) {
		BaseSearchDto<List<UserDto>> result = userService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody UserDto userDto){
		List<String> msg = insertValidation(userDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		UserDto result = userService.insert(userDto);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm người dùng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		
		return res;
	}
	

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserDto userDto) {
		 List<String> msg = updateValidation(userDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }
			
	        UserDto result = userService.update(userDto);
			ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật người dùng thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
			return res;
	}


	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		boolean result = userService.deleteById(id);			

		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa người dùng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}

	private List<String> insertValidation(UserDto userDto) {
        List<String> result = new ArrayList<>();
        
        if (!Validate.checkPhone(userDto.getPhone())) {
        	result.add("Số điện thoại sai định dạng");
        }
        
        if (userService.isExistPhone(userDto.getPhone())) {
            result.add("Số điện thoại đã tồn tại");
        }
        
        if (userService.isExistEmail(userDto.getEmail())) {
            result.add("Email đã tồn tại");
        }
  
        if (accountService.isExistUsername(userDto.getAccount().getUsername()))
        {
        	result.add("Tài khoản đã tồn tại");
        }
        
        
        return result;
    }
	
    private List<String> updateValidation(UserDto userDto) {
        List<String> result = new ArrayList<>();
      
        if (userService.isExistEmailIgnore(userDto.getEmail(), userDto.getId())) {
            result.add("Email đã tồn tại");
        }
        
        if (!Validate.checkPhone(userDto.getPhone())) {
        	result.add("Số điện thoại sai định dạng");
        }       
        
        if (userService.isExistPhoneIgnore(userDto.getPhone(), userDto.getId())) {
            result.add("Số điện thoại đã tồn tại");
        }
      
        if (accountService.isExistUsernameIgnore(userDto.getAccount().getUsername(), userDto.getAccount().getId()))
        {
        	result.add("Tài khoản đã tồn tại");
        }

        return result;
    }

}
	
	
	
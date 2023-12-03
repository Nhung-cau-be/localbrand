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

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.service.IUserTypeService;


@RestController
@RequestMapping("/user-type")
public class UserTypeController {
	@Autowired
	private IUserTypeService userTypeService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<UserTypeDto> result = userTypeService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		UserTypeDto result = userTypeService.getById(id);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Loại người dùng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}

	@GetMapping("/get-full/{id}")
	public ResponseEntity<?> getFullById(@PathVariable String id) {
		UserTypeFullDto result = userTypeService.getFullById(id);

		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Loại người dùng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
		return res;
	}
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<UserTypeDto>> searchDto) {
		BaseSearchDto<List<UserTypeDto>> result = userTypeService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody UserTypeFullDto userTypeDto) {
		 List<String> msg = insertValidation(userTypeDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }      
	            UserTypeFullDto result = userTypeService.insert(userTypeDto);
	    		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm loại người dùng thành công"), HttpStatus.OK.value(), result))
	                    : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm loại người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
	    		return res;
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserTypeFullDto userTypeDto) {
		List<String> msg = updateValidation(userTypeDto);
		if (msg.size() > 0) {
			return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
		}

		UserTypeFullDto result = userTypeService.update(userTypeDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật loại người dùng thành công"), HttpStatus.OK.value(), result))
				: ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật loại người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		return res;
    }
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		 List<String> msg = deleteValidation(id);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }

			boolean result = userTypeService.deleteById(id);
			ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa loại người dùng thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa loại người dùng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
			return res;
    }


	
	
    private List<String> insertValidation(UserTypeFullDto userTypeDto) {
        List<String> result = new ArrayList<>();

        if (userTypeService.isExistName(userTypeDto.getName())) {
            result.add("Tên đã tồn tại");
        }

        return result;
    }
	
	private List<String> updateValidation(UserTypeFullDto userTypeDto) {
        List<String> result = new ArrayList<>();
        
        if (userTypeService.isExistNameIgnore(userTypeDto.getName(), userTypeDto.getId())) {
            result.add("Tên đã tồn tại");
        }
     
        return result;
    }
	
	 private List<String> deleteValidation(String userTypeId) {
	        List<String> result = new ArrayList<>();

	        if (userTypeService.isUsing(userTypeId)) {
	            result.add("Loại người dùng đang được sử dụng, không được xoá");
	        }

	        return result;
	    }

}

	
	
	
	
	

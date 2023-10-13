package com.localbrand.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dtos.response.ProviderDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProviderService;


@RestController
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private IProviderService providerService;
	
	@GetMapping("")
    public ResponseEntity<?> getAll() {
		try {
			List<ProviderDto> result = providerService.getAll();
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Danh sách Provider "), 0, result));
		}catch (Exception e) {
			
	        return ResponseEntity.ok(new ResponseDto(List.of("không tìm thấy danh sách Provider "), 0, null));
		}
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
		try {
			ProviderDto result = providerService.getById(id);
			
			if (result != null)
				return ResponseEntity.ok(new ResponseDto(List.of("Provider theo id " + id ), 0, result));
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy provider theo ID " + id ), 400, null));
		}catch (Exception e) {
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy provider"), 400, null));
		}
    }
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ProviderDto providerDto) {
		try {
			if (providerService.isUsingCode(providerDto.getCode())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Mã code đã được sử dụng"), 400, null));
			}
			
			if (providerDto.getName().isBlank() || providerDto.getAddress().isBlank() || providerDto.getCode().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Không được để trống thông tin provider" ), 400, null));
			}
			
	        ProviderDto result = providerService.insert(providerDto);
	        if (result != null)
	        	return ResponseEntity.ok(new ResponseDto(List.of("Thêm thành công Provider" ), 0, result));
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Thêm không thành công Provider" ), 400, null));
		}catch (Exception e) {
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Thêm không thành công Provider" ), 400, null));
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProviderDto providerDto) {
		try {
			if (providerService.isUsingCode(providerDto.getCode())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Mã code đã được sử dụng"), 400, null));
			}
			
			if (providerDto.getName().isBlank() || providerDto.getAddress().isBlank() || providerDto.getCode().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Không được để trống thông tin provider" ), 400, null));
			}
			
	        ProviderDto result = providerService.update(providerDto);
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thành công provider" ), 0, result));
		}catch (Exception e) {
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Sửa không thành công provider" ), 0, null));
		}
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		try {
	        boolean result = providerService.deleteById(id);
	        
	        if (result) {
	        	return ResponseEntity.ok(new ResponseDto(List.of("Xóa thành công" ), 0, result));
	        }
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Id không tồn tại" ), 400, null));
		}catch (Exception e) {
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Xóa không thành công" ), 400, null));
		}
    }
}

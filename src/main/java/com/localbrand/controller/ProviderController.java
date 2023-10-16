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
			List<ProviderDto> result = providerService.getAll();
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Danh sách Provider "), HttpStatus.OK.value(), result));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
			ProviderDto result = providerService.getById(id);
			
			if (result != null)
				return ResponseEntity.ok(new ResponseDto(List.of("Provider theo id " + id ), HttpStatus.OK.value(), result));
			
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy provider theo ID " + id ), HttpStatus.BAD_REQUEST.value(), null));
    }
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ProviderDto providerDto) {
			if (providerService.isUsingCode(providerDto.getCode())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Mã code đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			
			if (providerDto.getName().isBlank() || providerDto.getAddress().isBlank() || providerDto.getCode().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Không được để trống thông tin provider" ), HttpStatus.BAD_REQUEST.value(), null));
			}
			
	        ProviderDto result = providerService.insert(providerDto);
	        if (result != null)
	        	return ResponseEntity.ok(new ResponseDto(List.of("Thêm thành công Provider" ), HttpStatus.OK.value(), result));
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Thêm không thành công Provider" ), HttpStatus.BAD_REQUEST.value(), null));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProviderDto providerDto) {
			if (providerService.isUsingCode(providerDto.getCode())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Mã code đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			
			if (providerDto.getName().isBlank() || providerDto.getAddress().isBlank() || providerDto.getCode().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Không được để trống thông tin provider" ), HttpStatus.BAD_REQUEST.value(), null));
			}
			
	        ProviderDto result = providerService.update(providerDto);
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thành công provider" ), HttpStatus.OK.value(), result));
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
	        boolean result = providerService.deleteById(id);
	        
	        if (result) {
	        	return ResponseEntity.ok(new ResponseDto(List.of("Xóa thành công" ), HttpStatus.OK.value(), result));
	        }
	        
	        return ResponseEntity.ok(new ResponseDto(List.of("Xóa không thành công" ), HttpStatus.BAD_REQUEST.value(), null));
    }
}

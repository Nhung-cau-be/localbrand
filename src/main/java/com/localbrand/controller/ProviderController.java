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
			
	        return ResponseEntity.badRequest().body(new ResponseDto(List.of("Không tìm thấy provider theo ID " + id ), HttpStatus.BAD_REQUEST.value(), null));
    }
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody ProviderDto providerDto) {
			if (providerService.isUsingCode(providerDto.getCode())) {
				return ResponseEntity.badRequest().body(new ResponseDto(List.of("Mã code đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
	        ProviderDto result = providerService.insert(providerDto);
	        if (result != null)
	        	return ResponseEntity.ok(new ResponseDto(List.of("Thêm thành công Provider" ), HttpStatus.OK.value(), result));
	        
	        return ResponseEntity.badRequest().body(new ResponseDto(List.of("Thêm không thành công Provider" ), HttpStatus.BAD_REQUEST.value(), null));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ProviderDto providerDto) {
		 	List<String> msg = updateValidation(providerDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }
		
	        ProviderDto result = providerService.update(providerDto);
	        if (result != null)
	        	return ResponseEntity.ok(new ResponseDto(List.of("Sửa thành công provider" ), HttpStatus.OK.value(), result));
	        return ResponseEntity.badRequest().body(new ResponseDto(List.of("Sửa không thành công Provider" ), HttpStatus.BAD_REQUEST.value(), null));
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
	        boolean result = providerService.deleteById(id);
	        
	        if (result) {
	        	return ResponseEntity.ok(new ResponseDto(List.of("Xóa thành công" ), HttpStatus.OK.value(), result));
	        }
	        
	        return ResponseEntity.badRequest().body(new ResponseDto(List.of("Xóa không thành công" ), HttpStatus.BAD_REQUEST.value(), null));
    }
	private List<String> updateValidation(ProviderDto providerDto) {
        List<String> result = new ArrayList<>();

        if (providerDto.getId().isBlank()) {
            result.add("Vui lòng thêm id provider");
            return result;
        }

        if (providerService.isUsingCodeIgnore(providerDto.getName(), providerDto.getId())) {
            result.add("Mã code đã tồn tại");
        }

        return result;
    }
}

package com.localbrand.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dal.entity.Provider;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ProviderDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProviderService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/provider")
@CrossOrigin(origins = "http://localhost:4200")
public class ProviderController {
	@Autowired
	private IProviderService providerService;
	
	@GetMapping("")
    public ResponseEntity<?> getAll() {
			List<ProviderDto> result = providerService.getAll();
			
	        return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }

	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<ProviderDto>> searchDto) {
		BaseSearchDto<List<ProviderDto>> result = providerService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	@GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
			ProviderDto result = providerService.getById(id);
			ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Nhà cung cấp không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
			return res;
	}
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody ProviderDto providerDto) {
			if (providerService.isExistCode(providerDto.getCode())) {
				return ResponseEntity.badRequest().body(new ResponseDto(List.of("Mã đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
	        ProviderDto result = providerService.insert(providerDto);
	        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm nhà cung cấp thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm nhà cung cấp thất bại"), HttpStatus.BAD_REQUEST.value(), null));     
	        return res;
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ProviderDto providerDto) {
		 	List<String> msg = updateValidation(providerDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }
		
	        ProviderDto result = providerService.update(providerDto);
	        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật nhà cung cấp thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật nhà cung cấp thất bại"), HttpStatus.BAD_REQUEST.value(), null));     
	        return res;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
	        boolean result = providerService.deleteById(id);
	        ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa nhà cung cấp thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa nhà cung cấp thất bại"), HttpStatus.BAD_REQUEST.value(), null));
	        return res;
	}
	private List<String> updateValidation(ProviderDto providerDto) {
        List<String> result = new ArrayList<>();

        if (providerDto.getId().isBlank()) {
            result.add("Vui lòng thêm id nhà cung cấp");
            return result;
        }

        if (providerService.isExistCodeIgnore(providerDto.getCode(), providerDto.getId())) {
            result.add("Mã đã tồn tại");
        }

        return result;
    }
}

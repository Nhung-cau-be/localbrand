package com.localbrand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.dtos.response.ResponseDto;

import com.localbrand.service.ICustomerTypeService;

@RestController
@RequestMapping("/customer_type")
public class CustomerTypeController {
	@Autowired
	private ICustomerTypeService customerTypeService;
	
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		try {
			List<CustomerTypeDto> result = customerTypeService.getAll();
	        return ResponseEntity.ok(new ResponseDto(List.of("Danh sách loại khách hàng "), HttpStatus.OK.value(), result));
		}
		catch (Exception e) {
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy loại khách hàng "),HttpStatus.BAD_REQUEST.value(), null));
		}
    }
	
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody CustomerTypeDto customerTypeDto) {
		try {
			if (customerTypeDto.getName().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Chưa điền tên loại khách hàng" ),HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerTypeDto.getStandardPoint() <= 0) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Nhập sai định dạng Standard_point" ), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerTypeDto.getDiscountPercent() <= 0 ) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Nhập sai định dạng Discound_percent" ), HttpStatus.BAD_REQUEST.value(), null));
			}
	        CustomerTypeDto result = customerTypeService.insert(customerTypeDto);
	        if (result != null)
	        	return ResponseEntity.ok(new ResponseDto(List.of("Thêm thành công loại khách hàng" ), HttpStatus.OK.value(), result));
	        return ResponseEntity.ok(new ResponseDto(List.of("Thêm thất bại loại khách hàng" ), HttpStatus.BAD_REQUEST.value(), null));
		}
		catch (Exception e) {
	        return ResponseEntity.ok(new ResponseDto(List.of("Thêm thất bại loại khách hàng" ), HttpStatus.BAD_REQUEST.value(), null));
		}
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CustomerTypeDto customerTypeDto) {
		try {
			if (customerTypeService.isUsingName(customerTypeDto.getName())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên loại khách hàng đã được sử dụng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerTypeDto.getId() == null || customerTypeDto.getId().isBlank()) {
		            return ResponseEntity.ok(new ResponseDto(List.of("ID loại khách hàng không hợp lệ"), HttpStatus.BAD_REQUEST.value(), null));
		    }
			if (customerTypeDto.getName().isBlank()) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Chưa điền tên loại khách hàng" ),HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerTypeDto.getStandardPoint() <= 0) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Nhập sai định dạng Standard_point" ), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerTypeDto.getDiscountPercent() <= 0 ) {
	            return ResponseEntity.ok(new ResponseDto(List.of("Nhập sai định dạng Discound_percent" ), HttpStatus.BAD_REQUEST.value(), null));
			}
			CustomerTypeDto result = customerTypeService.update(customerTypeDto);
			
			if (result != null) {
		        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thành công loại khách hàng"), HttpStatus.OK.value(), result));
		    } else {
		        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thất bại loại khách hàng"), HttpStatus.BAD_REQUEST.value(), null));
		    }
		}
			catch (Exception e) {
	        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thất bại loại khách hàng" ),  HttpStatus.BAD_REQUEST.value(), null));
		}
    }
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		try {
	        boolean result = customerTypeService.deleteById(id);
	        if (result == true) {
	        	return ResponseEntity.ok(new ResponseDto(List.of("Xóa thành công" ),  HttpStatus.OK.value(), result));
	        }
	        return ResponseEntity.ok(new ResponseDto(List.of("Id không tồn tại, xoá thất bại" ),  HttpStatus.BAD_REQUEST.value(), null));
		}catch (Exception e) {

	        return ResponseEntity.ok(new ResponseDto(List.of("Xóa thất bại" ), HttpStatus.BAD_REQUEST.value(), null));
		}
    }
}

	
	
	
	
	

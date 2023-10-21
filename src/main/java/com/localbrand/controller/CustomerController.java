package com.localbrand.controller;


import java.util.ArrayList;
import java.util.Arrays;
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

import com.localbrand.Validate;
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IAccountService;
import com.localbrand.service.ICustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	@Autowired IAccountService accountService;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		try {
			List<CustomerDto> result = customerService.getAll();
	        return ResponseEntity.ok(new ResponseDto(List.of("Danh sách khách hàng "), HttpStatus.OK.value(), result));
		}
		catch (Exception e) {
	        return ResponseEntity.ok(new ResponseDto(List.of("Không tìm thấy khách hàng "),HttpStatus.BAD_REQUEST.value(), null));
		}
   }


	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody CustomerDto customerDto){
		List<String> msg = insertValidation(customerDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		CustomerDto result = customerService.insert(customerDto);
		
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm khách hàng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		
		return res;
	}
	

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CustomerDto customerDto) {
		 List<String> msg = updateValidation(customerDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }
			
	        CustomerDto result = customerService.update(customerDto);
			ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật khách hàng thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
			return res;
	}


	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		boolean result = customerService.deleteById(id);			

		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa khách hàng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}

	private List<String> insertValidation(CustomerDto customerDto) {
        List<String> result = new ArrayList<>();
        
        if (!Validate.checkPhoneNumber(customerDto.getPhonenumber())) {
        	result.add("Số điện thoại sai định dạng");
        }
        
        if (customerService.isExitsPhoneNumber(customerDto.getPhonenumber())) {
            result.add("Số điện thoại đã tồn tại");
        }
        
        if (customerService.isExitsEmail(customerDto.getEmail())) {
            result.add("Email đã tồn tại");
        }
  
        if (accountService.isExitsUsername(customerDto.getAccount().getUsername()))
        {
        	result.add("Username đã tồn tại");
        }
        
        
        return result;
    }
	
    private List<String> updateValidation(CustomerDto customerDto) {
        List<String> result = new ArrayList<>();
        
        if (customerService.isExitsEmailIgnore(customerDto.getEmail(), customerDto.getId())) {
            result.add("Email đã tồn tại");
        }

        if (customerService.isExitsPhoneNumberIgnore(customerDto.getPhonenumber(), customerDto.getId())) {
            result.add("Số điện thoại đã tồn tại");
        }

        return result;
    }

}
	
	
	
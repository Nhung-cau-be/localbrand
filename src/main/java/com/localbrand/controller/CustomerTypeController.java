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

import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dal.repository.ICustomerRepository;
import com.localbrand.dal.repository.ICustomerTypeRepository;
import com.localbrand.dtos.response.CustomerTypeDto;
import com.localbrand.dtos.response.ResponseDto;

import com.localbrand.service.ICustomerTypeService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer-type")
public class CustomerTypeController {
	@Autowired
	private ICustomerTypeService customerTypeService;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;
	
	@GetMapping("")
	 public ResponseEntity<?> getAll() {
		List<CustomerTypeDto> result = customerTypeService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }
	
	
	@PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody CustomerTypeDto customerTypeDto) {
		 List<String> msg = insertValidation(customerTypeDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }      
	            CustomerTypeDto result = customerTypeService.insert(customerTypeDto);		
	    		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm loại khách hàng thành công"), HttpStatus.OK.value(), result))
	                    : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm loại khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
	    		return res;
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CustomerTypeDto customerTypeDto) {
		 List<String> msg = updateValidation(customerTypeDto);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }
	        
			CustomerTypeDto result = customerTypeService.update(customerTypeDto);
			ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật loại khách hàng thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật loại khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));		
			return res;
    }
	
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		 List<String> msg = deleteValidation(id);
	        if (msg.size() > 0) {
	            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
	        }

			boolean result = customerTypeService.deleteById(id);
			ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa loại khách hàng thành công"), HttpStatus.OK.value(), result))
	                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa loại khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
			return res;
    }


	
	
    private List<String> insertValidation(CustomerTypeDto customerTypeDto) {
        List<String> result = new ArrayList<>();

        if (customerTypeService.isExitsName(customerTypeDto.getName())) {
            result.add("Tên đã tồn tại");
        }
        
        if (customerTypeService.isExitsStandardPoint(customerTypeDto.getStandardPoint())) {
        	result.add("Không được trùng điểm tiêu chuẩn");
        }

        return result;
    }
	
	private List<String> updateValidation(CustomerTypeDto customerTypeDto) {
        List<String> result = new ArrayList<>();
        
        if (customerTypeService.isExitsNameIgnore(customerTypeDto.getName(), customerTypeDto.getId())) {
            result.add("Tên đã tồn tại");
        }
        
        if (customerTypeService.isExitsStandardPointIgnore(customerTypeDto.getStandardPoint(), customerTypeDto.getId())) {
        	result.add("Không được trùng điểm tiêu chuẩn");
        }

        return result;
    }
	
	
    private List<String> deleteValidation(String customerTypeId) {
        List<String> result = new ArrayList<>();

        if (customerTypeService.findById(customerTypeId).getStandardPoint() == 0) {
            result.add("Không thể xóa loại khách hàng có standardpoint = 0, ");
        }
        
        return result;
    }
}

	
	
	
	
	

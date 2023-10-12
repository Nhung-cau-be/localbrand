package com.localbrand.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.EmailValidator;
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
import com.localbrand.dtos.response.CustomerDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

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
		try {

			if (customerDto.getName().isBlank()) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerDto.getCustomerType() == null)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Loại khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerDto.getAccount() == null)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Account khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (!Validate.checkPhoneNumber(customerDto.getSdt())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Sdt sai định dạng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (!Validate.checkEmail(customerDto.getEmail())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Email sai định dạng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			CustomerDto result = customerService.insert(customerDto);
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm khách hàng thành công"), HttpStatus.OK.value(), result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Thêm khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody CustomerDto customerDto) {
		try {
			if (customerDto.getId() == null || customerDto.getId().isBlank()) {
		        return ResponseEntity.ok(new ResponseDto(List.of("ID khách hàng không hợp lệ"), HttpStatus.BAD_REQUEST.value(), null));
		        }
			if (customerDto.getName().isBlank()) {
				return ResponseEntity.ok(new ResponseDto(List.of("Tên khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerDto.getCustomerType() == null)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Loại khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (customerDto.getAccount() == null)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Account khách hàng không thể để trống"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (!Validate.checkPhoneNumber(customerDto.getSdt())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Sdt sai định dạng"), HttpStatus.BAD_REQUEST.value(), null));
			}
			if (!Validate.checkEmail(customerDto.getEmail())) {
				return ResponseEntity.ok(new ResponseDto(List.of("Email sai định dạng"), HttpStatus.BAD_REQUEST.value(), null));
			}

			CustomerDto result = customerService.update(customerDto);
			if (result != null) {
		        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thành công khách hàng"), HttpStatus.OK.value(), result));
		    } else {
		        return ResponseEntity.ok(new ResponseDto(List.of("Sửa thất bại khách hàng"), HttpStatus.BAD_REQUEST.value(), null));
		    }
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Sửa khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}


	@DeleteMapping("delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		try {
			boolean result = customerService.deleteById(id);

			if(result==true)
			{
				return ResponseEntity.ok(new ResponseDto(List.of("Xóa khách hàng thành công"), HttpStatus.OK.value(), result));
			}
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseDto(List.of("Xóa khách hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

	@GetMapping("/searchByName")
	    public ResponseEntity<?> searchByName (@RequestParam String name) {
	        List<CustomerDto> result = customerService.searchByName(name);
	        if (result.isEmpty())
	          	{
	            	return ResponseEntity.ok(new ResponseDto(List.of("Tìm kiếm theo tên thất bại"), HttpStatus.BAD_REQUEST.value(), null));
	            }
	            return ResponseEntity.ok(new ResponseDto(List.of("Tìm kiếm theo tên thành công"), HttpStatus.OK.value(), result)
	           );

	 }

	@GetMapping("/searchByPhoneNumber")
	    public ResponseEntity<?> searchByPhoneNumber (@RequestParam String sdt) {
		 		List<CustomerDto> result = customerService.searchByPhoneNumber(sdt);
		 		if (result.isEmpty())
	          	{
	            	return ResponseEntity.ok(new ResponseDto(List.of("Tìm kiếm theo sdt thất bại"), HttpStatus.BAD_REQUEST.value(), null));
	            }
	            return ResponseEntity.ok(new ResponseDto(List.of("Tìm kiếm theo sdt thành công"), HttpStatus.OK.value(), result)
	           );
	 }


}
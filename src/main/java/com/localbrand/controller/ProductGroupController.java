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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.ProductGroupDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.IProductGroupService;

@RestController
@RequestMapping("/product-group")
public class ProductGroupController {
	
	@Autowired
	private IProductGroupService productGroupService;
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		List<ProductGroupDto> result = productGroupService.getAll();
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@PostMapping("")
	public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<ProductGroupDto>> searchDto) {
		BaseSearchDto<List<ProductGroupDto>> result = productGroupService.findAll(searchDto);
		return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		ProductGroupDto result = productGroupService.getById(id);		

		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Nhóm sản phẩm không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));		
		return res;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ProductGroupDto productGroupDto){
        List<String> msg = insertValidation(productGroupDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
        
		ProductGroupDto result = productGroupService.insert(productGroupDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm nhóm sản phẩm thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductGroupDto productGroupDto) {
        List<String> msg = updateValidation(productGroupDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }
		
        ProductGroupDto result = productGroupService.update(productGroupDto);
		ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật nhóm sản phẩm thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteById(@RequestParam String id) {
		boolean result = productGroupService.deleteById(id);			

		ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa nhóm sản phẩm thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa nhóm sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));	
		return res;
	}
	
    private List<String> insertValidation(ProductGroupDto productGroupDto) {
        List<String> result = new ArrayList<>();

        if (productGroupService.isExistCode(productGroupDto.getCode())) {
            result.add("Mã đã tồn tại");
        }

        if (productGroupDto.getCategory() == null || productGroupDto.getCategory().getId().isBlank()) {
            result.add("Vui lòng chọn nhóm sản phẩm");
        }

        return result;
    }
	
    private List<String> updateValidation(ProductGroupDto productGroupDto) {
        List<String> result = new ArrayList<>();

        if (productGroupDto.getId().isBlank()) {
            result.add("Vui lòng nhập id nhóm sản phẩm");
        }

        if (productGroupService.isExistCodeIgnore(productGroupDto.getCode(), productGroupDto.getId())) {
            result.add("Mã đã tồn tại");
        }

        if (productGroupDto.getCategory() == null || productGroupDto.getCategory().getId().isBlank()) {
            result.add("Vui lòng chọn nhóm sản phẩm");
        }

        return result;
    }

}

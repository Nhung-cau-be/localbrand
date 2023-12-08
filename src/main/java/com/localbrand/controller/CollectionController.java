package com.localbrand.controller;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CollectionDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/collection")
@CrossOrigin(origins = "http://localhost:4200")
public class CollectionController {
    @Autowired
    private ICollectionService collectionService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<CollectionDto> result = collectionService.getAll();
        return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }

    @PostMapping("")
    public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<CollectionDto>> searchDto) {
        BaseSearchDto<List<CollectionDto>> result = collectionService.findAll(searchDto);
        return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        CollectionDto result = collectionService.getById(id);

        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Bộ sưu tập không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
        return res;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody CollectionDto collectionDto) {
        CollectionDto result = collectionService.insert(collectionDto);
        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm bộ sưu tập thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm bộ sưu tập thất bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody CollectionDto collectionDto) {
        List<String> msg = updateValidation(collectionDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }

        CollectionDto result = collectionService.update(collectionDto);
        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật bộ sưu tập thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật bộ sưu tập thất bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam String id) {
        List<String> msg = deleteValidation(id);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }

        boolean result = collectionService.deleteById(id);
        ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa bộ sưu tập thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa danh mục thất bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    private List<String> updateValidation(CollectionDto collectionDto) {
        List<String> result = new ArrayList<>();

        if (collectionDto.getId().isBlank()) {
            result.add("Vui lòng thêm id bộ sưu tập");
            return result;
        }

        return result;
    }

    private List<String> deleteValidation(String collectionId) {
        List<String> result = new ArrayList<>();

        if (collectionService.isUsing(collectionId)) {
            result.add("Danh mục đang được sử dụng");
        }

        return result;
    }
}

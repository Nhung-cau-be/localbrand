package com.localbrand.controller;


import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.OrderSearchDto;
import com.localbrand.dtos.response.*;
import com.localbrand.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> findAll(@RequestBody BaseSearchDto<List<OrderDto>> searchDto) {
        BaseSearchDto<List<OrderDto>> result = orderService.findAll(searchDto);
        return ResponseEntity.ok(new ResponseDto(List.of(""), HttpStatus.OK.value(), result));
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody OrderSearchDto searchDto) {
        OrderSearchDto search = orderService.search(searchDto);
        return ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), search));
    }

    @GetMapping("get-full/{id}")
    public ResponseEntity<?> getFullById( @PathVariable String id) {
        OrderFullDto result = orderService.getFullById(id);

        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Đơn hàng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
        return res;
    }

    @GetMapping("get-orders-full-by-customer/{customerId}")
    public ResponseEntity<?> getOrdersFullByCustomerId( @PathVariable String customerId) {
        List<OrderFullDto> result = orderService.getOrdersFullByCustomerId(customerId);

        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Khách hàng chưa có đơn hàng"), HttpStatus.BAD_REQUEST.value(), ""));
        return res;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody OrderFullDto orderFullDto) {
        List<String> msg = updateValidation(orderFullDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }

        OrderFullDto result = orderService.update(orderFullDto);
        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Cập nhật đơn hàng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Cập nhật đơn hàng thất bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    private List<String> updateValidation(OrderFullDto orderFullDto) {
        List<String> result = new ArrayList<>();

        if(orderFullDto.getStatus() == null) {
            result.add("Vui lòng chọn trạng thái đơn hàng");
        }

        return result;
    }
}

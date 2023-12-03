package com.localbrand.controller;

import com.localbrand.dtos.response.CartFullDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {
    @Autowired
    private ICartService cartService;

    @GetMapping("/get-by-customer-id/{customerId}")
    public ResponseEntity<?> getByCustomerId(HttpServletRequest request, @PathVariable String customerId) {
        CartFullDto result = cartService.getByCustomerId(request, customerId);

        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList(""), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Giỏ hàng không tồn tại"), HttpStatus.BAD_REQUEST.value(), ""));
        return res;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CartFullDto cartFullDto) {
        List<String> msg = createOrderValidation(cartFullDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }

        CartFullDto result = cartService.createOrder(cartFullDto);
        ResponseEntity<?> res  = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Tạo đơn hàng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Tạo đơn hàng thât bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    private List<String> createOrderValidation(CartFullDto cartFullDto) {
        List<String> result = new ArrayList<>();

        if(cartFullDto.getItems() == null || cartFullDto.getItems().isEmpty()) {
            result.add("Vui lòng thêm sản phẩm vào giỏ hàng");
        }

        return result;
    }
}

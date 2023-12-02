package com.localbrand.controller;

import com.localbrand.dtos.response.CartFullDto;
import com.localbrand.dtos.response.ResponseDto;
import com.localbrand.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
        return res;    }
}

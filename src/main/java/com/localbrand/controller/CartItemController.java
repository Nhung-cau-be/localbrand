package com.localbrand.controller;

import com.localbrand.dtos.response.CartItemDto;
import com.localbrand.service.ICartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.localbrand.dtos.response.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cart-item")
@CrossOrigin(origins = "http://localhost:4200")
public class CartItemController {
    @Autowired
    private ICartItemService cartItemService;

    @PostMapping("/insert/{customerId}")
    public ResponseEntity<?> insert(@PathVariable String customerId, @Valid @RequestBody CartItemDto cartItemDto) {
        List<String> msg = insertValidation(customerId, cartItemDto);
        if (msg.size() > 0) {
            return ResponseEntity.badRequest().body(new ResponseDto(msg, HttpStatus.BAD_REQUEST.value(), ""));
        }

        CartItemDto result = cartItemService.insert(customerId, cartItemDto);
        ResponseEntity<?> res = result != null ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Thêm sản phẩm vào giỏ hàng thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Thêm sản phầm vào giỏ hàng thất bại"), HttpStatus.BAD_REQUEST.value(), ""));

        return res;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam String id) {
        boolean result = cartItemService.deleteById(id);

        ResponseEntity<?> res  = result ? ResponseEntity.ok(new ResponseDto(Arrays.asList("Xóa sản phẩm thành công"), HttpStatus.OK.value(), result))
                : ResponseEntity.badRequest().body(new ResponseDto(Arrays.asList("Xóa sản phẩm thất bại"), HttpStatus.BAD_REQUEST.value(), null));
        return res;
    }

    private List<String> insertValidation(String customerId, CartItemDto cartItemDto) {
        List<String> result = new ArrayList<>();

        if (cartItemDto.getQuantity() > cartItemDto.getProductSKU().getQuantity())
            result.add("Vui lòng chọn số lượng sản phẩm phù hợp");

        if (!cartItemService.checkCartQuantity(customerId, cartItemDto))
            result.add("Số lượng sản phẩm sau khi thêm trong giỏ hàng vượt quá số sản phẩm trong kho");

        return result;
    }
}

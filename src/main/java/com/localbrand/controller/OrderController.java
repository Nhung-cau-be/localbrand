package com.localbrand.controller;


import com.localbrand.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    @Autowired
    private IOrderService orderService;
}

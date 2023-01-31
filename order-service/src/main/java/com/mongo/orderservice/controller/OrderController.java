package com.mongo.orderservice.controller;

import com.mongo.orderservice.dto.OrderRequest;
import com.mongo.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.PlaceOrder(orderRequest);
        return "Order placed successfully" ;
    }
}

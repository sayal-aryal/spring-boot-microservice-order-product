package com.miu.orderservice.controller;

import com.miu.orderservice.response.ProductResponse;
import com.miu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


        @PostMapping("/products/{id}")
        public ResponseEntity<String> place(@PathVariable("id") Long productId) {
            orderService.placeOrderByProductId(productId);
            String message = "Order placed successfully for product ID: " + productId;
            return ResponseEntity.ok(message);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> cancel(@PathVariable("id") Long id) {
            String message;
            if(orderService.cancel(id)) {
                 message = "Order cancelled successfully for order ID: " + id;
            } else{
                message = "Unable of Order of "+id+" has been already cancelled " ;
            }


            return ResponseEntity.ok(message);
        }


}

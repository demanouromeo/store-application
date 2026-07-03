package com.dmsacad.store.controllers;

import java.util.List;

import com.dmsacad.store.exceptions.OrderNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmsacad.store.dtos.response.OrderDto;
import com.dmsacad.store.services.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrdersForCurrentUser();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable
            @NotNull
            @Min(1)
            Long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}

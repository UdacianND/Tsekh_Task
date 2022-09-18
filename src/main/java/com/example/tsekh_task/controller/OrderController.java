package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.OrderService;
import com.example.tsekh_task.payload.request.AddOrderDto;
import com.example.tsekh_task.payload.request.UpdateOrderDto;
import com.example.tsekh_task.payload.response.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{companyId}/getOrders")
    public ResponseEntity<?> getOrders(
            @PathVariable Long companyId,
            @RequestParam boolean isActive,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo,
            @RequestParam int page)
    {
        List<OrderDto> orders = orderService.getOrders(companyId,isActive, dateFrom, dateTo, page);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(
            @PathVariable Long id)
    {
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @PostMapping("/{companyId}/addOrder")
    public ResponseEntity<?> addOrder(
            @PathVariable Long companyId,
            @RequestBody AddOrderDto orderDto){
        orderService.addOrder(companyId,orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("updateOrder/{orderId}")
    public ResponseEntity<?> updateOrder(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderDto orderDto){
        orderService.updateOrder(orderId, orderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

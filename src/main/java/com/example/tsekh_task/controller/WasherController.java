package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.WasherService;
import com.example.tsekh_task.payload.request.AddWasherDto;
import com.example.tsekh_task.payload.request.UpdateWasherDto;
import com.example.tsekh_task.payload.response.OrderDto;
import com.example.tsekh_task.payload.response.WasherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/washer")
public class WasherController {
    private final WasherService washerService;

    @PostMapping("/avatar/{washerId}")
    public ResponseEntity<?> setUserAvatar(
            @PathVariable Long washerId,
            @RequestParam MultipartFile image) throws Exception {
        washerService.setWasherAvatar(washerId, image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{companyId}/getWashers")
    public ResponseEntity<?> getWashers(
            @PathVariable Long companyId,
            @RequestParam String name,
            @RequestParam int page){
        List<WasherDto> washers = washerService.getWashers(companyId, name, page);
        return new ResponseEntity<>(washers,HttpStatus.OK);
    }

    @GetMapping("/{washerId}")
    public ResponseEntity<?> getWasher(@PathVariable Long washerId){
        WasherDto washer = washerService.getWasher(washerId);
        return new ResponseEntity<>(washer,HttpStatus.OK);
    }

    @GetMapping("/{washerId}/getOrders")
    public ResponseEntity<?> getOrders(
            @PathVariable Long washerId,
            @RequestParam boolean isActive,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo,
            @RequestParam int page){
        List<OrderDto> orders = washerService.getOrders(washerId,isActive, dateFrom, dateTo, page);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("{companyId}/add")
    public ResponseEntity<?> addWasher(
            @PathVariable Long companyId,
            @RequestBody AddWasherDto washerDto){
        washerService.addWasher(companyId, washerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("update/{washerId}")
    public ResponseEntity<?> updateWasher(
            @PathVariable Long washerId,
            @RequestBody UpdateWasherDto washerDto){
        washerService.updateWasher(washerId, washerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

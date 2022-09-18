package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.OrderService;
import com.example.tsekh_task.base_service.WashCompanyService;
import com.example.tsekh_task.payload.request.AddWashCompanyDto;
import com.example.tsekh_task.payload.response.AnalyticsDto;
import com.example.tsekh_task.payload.response.OrderDto;
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
@RequestMapping("api/washCompany")
public class WashCompanyController {
    private final WashCompanyService washCompanyService;
    private final OrderService orderService;

    @PostMapping("addCompany")
    public ResponseEntity<?> add(@RequestBody AddWashCompanyDto washCompanyDto)
    {
        washCompanyService.addCompany(washCompanyDto);
        String responseMessage = "Company was added successfully";
        return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
    }

    @PostMapping("getId")
    public ResponseEntity<?> getId(){
        List<Long> companyIds = washCompanyService.getCompanyIds();
        return new ResponseEntity<>(companyIds, HttpStatus.OK);
    }

    @PostMapping("/avatar/{companyId}")
    public ResponseEntity<?> setUserAvatar(
            @PathVariable Long companyId,
            @RequestParam MultipartFile image){
        washCompanyService.setCompanyAvatar(companyId, image);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{companyId}/analytics")
    public ResponseEntity<?> getAnalytics(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo){
        AnalyticsDto analyticsDto = orderService.getAnalytics(companyId, dateFrom, dateTo);
        return new ResponseEntity<>(analyticsDto,HttpStatus.OK);
    }

}

package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.CompanyServicesService;
import com.example.tsekh_task.payload.request.AddServiceDto;
import com.example.tsekh_task.payload.request.UpdateServiceDto;
import com.example.tsekh_task.payload.response.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/service")
public class ServiceController {

    private final CompanyServicesService companyServicesService;

    @GetMapping("/{companyId}/getServices")
    public ResponseEntity<?> getServices(
            @PathVariable Long companyId,
            @RequestParam String searchName,
            @RequestParam int page){
        List<ServiceDto> services = companyServicesService.getServices(companyId, searchName, page);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getService(@PathVariable Long id){
        ServiceDto service = companyServicesService.getService(id);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @PostMapping("/{companyId}/add")
    public ResponseEntity<?> addService(
            @PathVariable Long companyId,
            @RequestBody AddServiceDto serviceDto){
        companyServicesService.addService(companyId, serviceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> updateService(
            @PathVariable Long id,
            @RequestBody UpdateServiceDto serviceDto){
        companyServicesService.updateService(id, serviceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

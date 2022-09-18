package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.AddServiceDto;
import com.example.tsekh_task.payload.request.UpdateServiceDto;
import com.example.tsekh_task.payload.response.ServiceDto;

import java.util.List;

public interface CompanyServicesService {
    List<ServiceDto> getServices(Long companyId, String searchName, int page);

    ServiceDto getService(Long id);

    void addService(Long companyId, AddServiceDto serviceDto);

    void updateService(Long id, UpdateServiceDto serviceDto);
}

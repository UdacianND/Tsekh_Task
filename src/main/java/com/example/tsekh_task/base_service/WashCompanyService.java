package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.AddWashCompanyDto;
import com.example.tsekh_task.payload.response.AnalyticsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface WashCompanyService {
    void addCompany(AddWashCompanyDto washCompanyDto);
    List<Long> getCompanyIds();
    void setCompanyAvatar(Long companyId, MultipartFile image);
}

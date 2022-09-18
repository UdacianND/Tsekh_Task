package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.AddWasherDto;
import com.example.tsekh_task.payload.request.UpdateWasherDto;
import com.example.tsekh_task.payload.response.OrderDto;
import com.example.tsekh_task.payload.response.WasherDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface WasherService {
    void setWasherAvatar(Long washerId, MultipartFile image) throws Exception;

    List<WasherDto> getWashers(Long companyId, String name, int page);

    WasherDto getWasher(Long washerId);

    List<OrderDto> getOrders(Long washerId, boolean isActive, Date dateFrom, Date dateTo, int page);

    void addWasher(Long companyId, AddWasherDto washerDto);

    void updateWasher(Long washerId, UpdateWasherDto washerDto);
}

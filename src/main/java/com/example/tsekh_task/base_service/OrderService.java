package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.AddOrderDto;
import com.example.tsekh_task.payload.request.UpdateOrderDto;
import com.example.tsekh_task.payload.response.AnalyticsDto;
import com.example.tsekh_task.payload.response.OrderDto;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders(Long companyId, boolean isActive, Date dateFrom, Date dateTo, int page);

    OrderDto getOrder(Long id);

    void addOrder(Long companyId, AddOrderDto orderDto);

    void updateOrder(Long orderId, UpdateOrderDto orderDto);

    AnalyticsDto getAnalytics(Long companyId, Date dateFrom, Date dateTo);
}

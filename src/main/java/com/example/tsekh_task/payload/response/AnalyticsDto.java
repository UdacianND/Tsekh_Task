package com.example.tsekh_task.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsDto {
    private int totalOrders;
    private int totalWashers;
    private int ordersSum;
    private double washersSum;
}

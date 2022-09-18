package com.example.tsekh_task.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JournalDto {
    private Long orderId;
    private String userName;
    private String previousCarModel;
    private String currentCarModel;
    private String previousCarNumber;
    private String currentCarNumber;
    private List<String> previousServicesName;
    private List<String> currentServicesName;
    private List<Integer> previousServicesPrice;
    private List<Integer> currentServicesPrice;
    private int previousPrice;
    private int currentPrice;
    private boolean isCancelled;
    private String  cancelledReason;
}

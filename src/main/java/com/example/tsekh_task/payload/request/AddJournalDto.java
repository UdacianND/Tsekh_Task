package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddJournalDto {
    @NotNull
    private Long orderId;
    @NotNull
    private String userName;
    @NotNull
    private String previousCarModel;
    @NotNull
    private String currentCarModel;
    @NotNull
    private String previousCarNumber;
    @NotNull
    private String currentCarNumber;
    @NotNull
    private List<String> previousServicesName;
    private List<String> currentServicesName;
    private List<Integer> previousServicesPrice;
    private List<Integer> currentServicesPrice;
    private int previousPrice;
    private int currentPrice;
    private boolean isCancelled;
    private String  cancelledReason;
}

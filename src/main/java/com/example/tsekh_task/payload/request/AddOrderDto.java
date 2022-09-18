package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class AddOrderDto {
    @NotNull
    private Set<Long> serviceIds;
    @NotNull
    private Set<Long> washerIds;
    @NotNull
    private String carModel;
    @NotNull
    private String carNumber;
}

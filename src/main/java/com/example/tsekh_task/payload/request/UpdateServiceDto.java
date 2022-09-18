package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateServiceDto {
    @NotNull
    private String name;
    @NotNull
    private int duration;
    @NotNull
    private int price;
}

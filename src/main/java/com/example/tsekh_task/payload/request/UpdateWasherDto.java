package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateWasherDto {
    @NotNull
    private String name;
    @NotNull
    private int telephoneNumber;
    @NotNull
    private int stake;
}

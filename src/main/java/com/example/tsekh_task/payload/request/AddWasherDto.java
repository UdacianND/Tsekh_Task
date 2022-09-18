package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddWasherDto {
    private String name;
    private int telephoneNumber;
    private int stake;
}

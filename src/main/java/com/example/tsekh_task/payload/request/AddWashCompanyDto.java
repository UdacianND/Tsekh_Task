package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddWashCompanyDto {
    @NotNull
    private String name;
    @NotNull
    private String location;
}

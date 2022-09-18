package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserSignUpDto {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
}


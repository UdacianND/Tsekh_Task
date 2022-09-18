package com.example.tsekh_task.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRoleDto {
    @NotNull
    Long userId;
    @NotNull
    String role;
}

package com.example.tsekh_task.base_service;


import com.example.tsekh_task.payload.request.UserRoleDto;
import com.example.tsekh_task.payload.request.UserSignUpDto;

public interface UserService {
    void setUserRole(Long companyId, UserRoleDto user) throws RuntimeException;
    void registerUser(UserSignUpDto user);
    void logout();
}

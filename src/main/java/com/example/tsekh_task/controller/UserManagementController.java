package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.UserService;
import com.example.tsekh_task.payload.request.UserRoleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user-management")
public class UserManagementController {

    private final UserService userService;

    @PostMapping("/{companyId}/userRole")
    public ResponseEntity<?> setUserRole(
            @PathVariable Long companyId,
            @RequestBody UserRoleDto user
    ){
        userService.setUserRole(companyId,user);
        return ResponseEntity.ok().body(null);
    }
}

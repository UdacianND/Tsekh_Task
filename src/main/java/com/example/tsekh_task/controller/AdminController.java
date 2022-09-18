package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.AdminService;
import com.example.tsekh_task.payload.request.ChangePasswordDto;
import com.example.tsekh_task.payload.request.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.AuthenticationFailedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageDto mail){
        adminService.sendMessage(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto passwordDto) throws AuthenticationFailedException {
        adminService.changePassword(passwordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

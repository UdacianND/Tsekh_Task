package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.ChangePasswordDto;
import com.example.tsekh_task.payload.request.SendMessageDto;

import javax.mail.AuthenticationFailedException;

public interface AdminService {
    void sendMessage(SendMessageDto mail);

    void changePassword(ChangePasswordDto passwordDto) throws AuthenticationFailedException;
}

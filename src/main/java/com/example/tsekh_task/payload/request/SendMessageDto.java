package com.example.tsekh_task.payload.request;

import lombok.Data;

@Data
public class SendMessageDto {
    private String contact;
    private String message;
    private String toEmail;
}

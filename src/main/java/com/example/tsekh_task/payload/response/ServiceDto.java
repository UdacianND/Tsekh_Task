package com.example.tsekh_task.payload.response;

import com.example.tsekh_task.entity.Service;
import lombok.Data;

@Data
public class ServiceDto {
    private String name;
    private int duration;
    private int price;

    public ServiceDto(Service service) {
        this.name = service.getName();
        this.duration = service.getDuration();
        this.price = service.getPrice();
    }
}

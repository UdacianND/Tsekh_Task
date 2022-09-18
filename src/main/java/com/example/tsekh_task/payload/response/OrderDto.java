package com.example.tsekh_task.payload.response;

import com.example.tsekh_task.entity.Order;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Set<ServiceDto> services;
    private Set<WasherDto> washers;
    private int price;
    private String carModel;
    private String carNumber;
    private String clientName;
    private int clientNumber;
    private boolean isActive;
    private boolean isCancelled;
    private Date date;

    public OrderDto(Set<ServiceDto> services, Set<WasherDto> washers, Order order) {
        this.services = services;
        this.washers = washers;
        this.price = order.getPrice();
        this.carModel = order.getCarModel();
        this.carNumber = order.getCarNumber();
        this.clientName = order.getClientName();
        this.clientNumber = order.getClientNumber();
        this.isActive = order.isActive();
        this.isCancelled = order.isCancelled();
        this.date = order.getDate();
    }

    public static OrderDto of(Order order){
        Set<ServiceDto> serviceDtoList = order.getServices()
                .stream().map(ServiceDto::new).collect(Collectors.toSet());
        Set<WasherDto> washerDtoList = order.getWashers()
                .stream().map(WasherDto::new).collect(Collectors.toSet());
        return new OrderDto(serviceDtoList, washerDtoList, order);
    }
}

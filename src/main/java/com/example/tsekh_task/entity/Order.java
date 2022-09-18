package com.example.tsekh_task.entity;

import com.example.tsekh_task.payload.request.AddOrderDto;
import com.example.tsekh_task.payload.response.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToMany
    private Set<Service> services;
    @ManyToMany
    private Set<Washer> washers;
    @NotNull
    private int price;
    @NotNull
    private String carModel;
    @NotNull
    private String carNumber;
    @NotNull
    private String clientName;
    @Column(unique = true, nullable = false, columnDefinition = "serial")
    @Generated(GenerationTime.ALWAYS)
    private int clientNumber;
    private boolean isActive;
    private boolean isCancelled;
    private Date date;
    @ManyToOne
    private WashCompany washCompany;

    public Order(Set<Service> services, Set<Washer> washers, int price, AddOrderDto orderDto, String clientName) {
        this.services = services;
        this.washers = washers;
        this.price = price;
        this.carModel = orderDto.getCarModel();
        this.carNumber = orderDto.getCarNumber();
        this.clientName = clientName;
        this.isActive = true;
        this.isCancelled = false;
        this.date = new Date();
    }

}

package com.example.tsekh_task.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int duration;
    @NotNull
    private int price;
    @ManyToMany(fetch=FetchType.LAZY, mappedBy = "services")
    private List<Order> order;
    @ManyToOne
    private WashCompany washCompany;

    public Service(String name, int duration, int price, WashCompany washCompany) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.washCompany = washCompany;
    }
}

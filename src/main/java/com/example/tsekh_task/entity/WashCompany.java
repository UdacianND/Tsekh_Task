package com.example.tsekh_task.entity;

import com.example.tsekh_task.entity.user.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "wash_company")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WashCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String name;
    private byte[] avatar;
    @NotNull
    private String location;

    public WashCompany(String name, String location) {
        this.name = name;
        this.location = location;
    }
}


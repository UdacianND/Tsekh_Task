package com.example.tsekh_task.entity;

import com.example.tsekh_task.payload.request.AddWasherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Washer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int telephoneNumber;
    private int stake;
    private byte[] image;
    private boolean isActive;
    @ManyToMany(fetch=FetchType.LAZY,mappedBy = "washers")
    private List<Order> order;
    @ManyToOne
    private WashCompany washCompany;

    public Washer(AddWasherDto washerDto, WashCompany washCompany) {
        this.name = washerDto.getName();
        this.telephoneNumber = washerDto.getTelephoneNumber();
        this.stake = washerDto.getStake();
        this.washCompany = washCompany;
        this.isActive = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Washer washer)) return false;
        return getId().equals(washer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

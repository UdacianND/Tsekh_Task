package com.example.tsekh_task.entity.journal;

import com.example.tsekh_task.entity.Order;
import com.example.tsekh_task.entity.WashCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @ManyToOne
    private WashCompany washCompany;
    @NotNull
    @Column(name="changes", length = 10000)
    private String changes;

    public Journal(WashCompany washCompany, String changes) {
        this.washCompany = washCompany;
        this.changes = changes;
    }
}

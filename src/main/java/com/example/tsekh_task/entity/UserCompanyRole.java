package com.example.tsekh_task.entity;

import com.example.tsekh_task.entity.user.Role;
import com.example.tsekh_task.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * This entity defines user role in company.
 * User can have different role in different companies
 **/

@Entity(name = "user_company_role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "wash_company_id"})
)
public class UserCompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private WashCompany washCompany;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserCompanyRole(User user, WashCompany washCompany, Role role) {
        this.user = user;
        this.washCompany = washCompany;
        this.role = role;
    }
}

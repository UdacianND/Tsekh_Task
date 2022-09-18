package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.WashCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WashCompanyRepository extends JpaRepository<WashCompany,Long> {

    @Query("select w.id from wash_company w inner join " +
            "user_company_role u on u.washCompany.id = w.id where" +
            " u.user.role = 'OWNER' and u.user.id = :ownerId")
    List<Long> getCompanyIds(@Param("ownerId") Long ownerId);
}


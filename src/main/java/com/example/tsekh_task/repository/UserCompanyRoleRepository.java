package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.UserCompanyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCompanyRoleRepository extends JpaRepository<UserCompanyRole,Long> {
    @Query("select u from user_company_role u " +
            "where u.user.id = :userId and u.washCompany.id = :companyId")
    Optional<UserCompanyRole> getUserPermission(
            @Param("userId") Long userId,
            @Param("companyId") Long companyId);
}

package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.Service;
import com.example.tsekh_task.entity.Washer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    @Query("select s from Service s where lower(s.name) " +
            "like lower(concat('%', :name,'%')) and s.washCompany.id =:companyId")
    List<Service> getServiceByName(@Param("name") String name,
                                   @Param("companyId") Long companyId,
                                   Pageable page);
}

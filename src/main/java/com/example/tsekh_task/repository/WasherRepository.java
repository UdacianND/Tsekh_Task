package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.Washer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WasherRepository extends JpaRepository<Washer,Long> {
    @Query("select w from Washer w where lower(w.name) " +
            "like lower(concat('%', :name,'%')) and w.washCompany.id =:companyId")
    List<Washer> getWashersByName(@Param("name") String name,
                                  @Param("companyId") Long companyId,
                                  Pageable page);
}

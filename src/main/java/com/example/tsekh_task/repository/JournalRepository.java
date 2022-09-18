package com.example.tsekh_task.repository;

import com.example.tsekh_task.entity.journal.Journal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal,Long> {
    @Query("select j from Journal j where j.washCompany.id =:companyId")
    List<Journal> getJournal(@Param("companyId") Long companyId,
                              Pageable page);
}

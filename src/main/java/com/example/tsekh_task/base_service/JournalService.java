package com.example.tsekh_task.base_service;

import com.example.tsekh_task.payload.request.AddJournalDto;
import com.example.tsekh_task.payload.response.JournalDto;

import java.util.List;

public interface JournalService {
    void addJournal(Long companyId, AddJournalDto addJournalDto);

    List<JournalDto> getJournals(Long companyId, int page);
}

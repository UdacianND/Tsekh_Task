package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.JournalService;
import com.example.tsekh_task.payload.request.AddJournalDto;
import com.example.tsekh_task.payload.response.JournalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/journal")
public class JournalController {
    private final JournalService journalService;

    @PostMapping("{companyId}/add")
    public ResponseEntity<?> addJournal(
            @PathVariable Long companyId,
            @RequestBody AddJournalDto addJournalDto){
        journalService.addJournal(companyId, addJournalDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{companyId}/getJournals")
    public ResponseEntity<?> getJournals(
            @PathVariable Long companyId,
            @RequestParam int page){
        List<JournalDto> journals = journalService.getJournals(companyId, page);
        return new ResponseEntity<>(journals,HttpStatus.OK);
    }
}

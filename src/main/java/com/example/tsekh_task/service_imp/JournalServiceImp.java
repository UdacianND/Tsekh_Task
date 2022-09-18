package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.JournalService;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.journal.Journal;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.AddJournalDto;
import com.example.tsekh_task.payload.response.JournalDto;
import com.example.tsekh_task.repository.JournalRepository;
import com.example.tsekh_task.repository.WashCompanyRepository;
import com.example.tsekh_task.utils.AuthorizationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalServiceImp implements JournalService {
    private final ObjectMapper objectMapper;
    private final JournalRepository journalRepository;
    private final AuthorizationFilter authorizationFilter;
    private final WashCompanyRepository washCompanyRepository;

    @Override
    public void addJournal(Long companyId, AddJournalDto addJournalDto) {
        WashCompany washCompany = washCompanyRepository.findById(companyId)
                .orElseThrow(() ->new ObjectNotFoundException(companyId, "washCompany"));
        Long userId = getPrincipal().getId();
        authorizationFilter.checkUserAccessToCompany(companyId,userId);
        String changes = dtoToString(addJournalDto);
        Journal journal = new Journal(washCompany, changes);
        journalRepository.save(journal);
    }

    @Override
    public List<JournalDto> getJournals(Long companyId, int pageNumber) {
        try{
            Long userId = getPrincipal().getId();
            authorizationFilter.checkUserAccessToCompany(companyId,userId);
            Pageable page = PageRequest.of(pageNumber, 10);
            return journalRepository.getJournal(companyId, page)
                    .stream().map(this::getJournalDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    private User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private  String dtoToString(AddJournalDto journal){
        try {
            return objectMapper.writeValueAsString(journal);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("AddJournalDto is not a valid");
        }
    }

    private JournalDto getJournalDto(Journal journal){
        try {
            return objectMapper.readValue(journal.getChanges(), JournalDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid journal data");
        }
    }
}

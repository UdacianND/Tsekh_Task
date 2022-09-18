package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.WashCompanyService;
import com.example.tsekh_task.entity.UserCompanyRole;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.exception.InvalidFileFormatException;
import com.example.tsekh_task.payload.request.AddWashCompanyDto;
import com.example.tsekh_task.payload.response.AnalyticsDto;
import com.example.tsekh_task.payload.response.OrderDto;
import com.example.tsekh_task.repository.UserCompanyRoleRepository;
import com.example.tsekh_task.repository.UserRepository;
import com.example.tsekh_task.repository.WashCompanyRepository;
import com.example.tsekh_task.utils.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.example.tsekh_task.entity.user.Role.OWNER;
import static com.example.tsekh_task.utils.MediaService.checkFileIsImage;

@Service
@RequiredArgsConstructor
public class WashCompanyServiceImp implements WashCompanyService{

    private final UserRepository userRepository;
    private final WashCompanyRepository washCompanyRepository;
    private final UserCompanyRoleRepository userRoleRepository;
    private final AuthorizationFilter authorizationFilter;

    @Override
    public void addCompany(AddWashCompanyDto washCompanyDto) {
        User principal = getPrincipal();
        WashCompany washCompany = new WashCompany(washCompanyDto.getName(), washCompanyDto.getLocation());
        washCompanyRepository.save(washCompany);
        UserCompanyRole userRole = new UserCompanyRole(principal,washCompany, OWNER);
        userRoleRepository.save(userRole);
        principal.setRole(OWNER);
        userRepository.save(principal);
    }

    @Override
    public List<Long> getCompanyIds() {
        return washCompanyRepository.getCompanyIds(getPrincipal().getId());
    }

    @Override
    public void setCompanyAvatar(Long companyId, MultipartFile image) {
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        WashCompany washCompany = washCompanyRepository.findById(companyId)
                .orElseThrow(() -> new ObjectNotFoundException(companyId, "company"));
        checkFileIsImage(image);
        try {
            washCompany.setAvatar(image.getBytes());
        } catch (IOException e) {
            throw new InvalidFileFormatException();
        }
        washCompanyRepository.save(washCompany);
    }

    private User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

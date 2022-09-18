package com.example.tsekh_task.utils;

import com.example.tsekh_task.entity.UserCompanyRole;
import com.example.tsekh_task.entity.user.Role;
import com.example.tsekh_task.repository.UserCompanyRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationFilter {
    private final UserCompanyRoleRepository userRoleRepository;

    public void checkUserAccessToCompany(Long companyId, Long userId){
        String message = "User has no permissions to company " + companyId;
        UserCompanyRole userCompanyRole = userRoleRepository.getUserPermission(userId, companyId)
                .orElseThrow(()->new AccessDeniedException(message));
        if(userCompanyRole.getRole().equals(Role.USER))
            throw new AccessDeniedException("Admin role is required to access company " + companyId);
    }
}

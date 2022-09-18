package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.UserService;
import com.example.tsekh_task.entity.UserCompanyRole;
import com.example.tsekh_task.entity.WashCompany;
import com.example.tsekh_task.entity.user.Role;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.exception.UserAlreadyExistsException;
import com.example.tsekh_task.payload.request.UserRoleDto;
import com.example.tsekh_task.payload.request.UserSignUpDto;
import com.example.tsekh_task.repository.UserCompanyRoleRepository;
import com.example.tsekh_task.repository.UserRepository;
import com.example.tsekh_task.repository.WashCompanyRepository;
import com.example.tsekh_task.utils.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.tsekh_task.entity.user.Role.USER;
import static com.example.tsekh_task.entity.user.UserStatus.LOGGED_OUT;


@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WashCompanyRepository washCompanyRepository;
    private final AuthorizationFilter   authorizationFilter;
    private final UserCompanyRoleRepository userRoleRepository;

    @Override
    public void setUserRole(Long companyId, UserRoleDto userDto){
        WashCompany washCompany = washCompanyRepository.findById(companyId).orElseThrow(
                () -> new ObjectNotFoundException(companyId, "washCompany"));
        authorizationFilter.checkUserAccessToCompany(companyId,getPrincipal().getId());
        Long userId = userDto.getUserId();
        Role role = Role.valueOf(userDto.getRole());
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException(userId,"User"));
        user.setRole(role);
        UserCompanyRole userRole = new UserCompanyRole(user,washCompany,role);
        userRoleRepository.save(userRole);
        userRepository.save(user);
    }

    @Override
    public void registerUser(UserSignUpDto user) {
        boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
        if(existsByUsername)
            throw new UserAlreadyExistsException("User already exists with username " + user.getUsername());
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
        if (existsByEmail)
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        saveUser(user);
    }

    @Override
    public void logout() {
        User principal = getPrincipal();
        principal.setStatus(LOGGED_OUT);
        userRepository.save(principal);
    }

    private void saveUser(UserSignUpDto userDto){
        User user = new User(
                userDto.getUsername(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                USER);
        userRepository.save(user);
    }

    public User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

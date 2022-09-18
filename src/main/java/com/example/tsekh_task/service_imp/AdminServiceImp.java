package com.example.tsekh_task.service_imp;

import com.example.tsekh_task.base_service.AdminService;
import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.ChangePasswordDto;
import com.example.tsekh_task.payload.request.SendMessageDto;
import com.example.tsekh_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendMessage(SendMessageDto mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getContact());
        message.setTo(mail.getToEmail());
        message.setText(mail.getMessage());
        emailSender.send(message);
    }

    @Override
    public void changePassword(ChangePasswordDto passwordDto) throws AuthenticationFailedException {
        User principal = getPrincipal();
        boolean matches = passwordEncoder.matches(passwordDto.getOldPassword(), principal.getPassword());
        if(!matches)
            throw new AuthenticationFailedException("Password change failed: " + passwordDto.getOldPassword());
        principal.setPassword(passwordDto.getNewPassword());
        userRepository.save(principal);
    }

    private User getPrincipal(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

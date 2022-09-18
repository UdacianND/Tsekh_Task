package com.example.tsekh_task.jwt;

import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.payload.request.UserLoginDto;
import com.example.tsekh_task.payload.response.UserAuthDto;
import com.example.tsekh_task.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.tsekh_task.entity.user.UserStatus.LOGGED_IN;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;


    public UserAuthDto authenticateUser(UserLoginDto userDto){
            Authentication authenticate = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword()));
            User principal = (User) authenticate.getPrincipal();
            String token = Jwts.builder()
                    .setSubject(principal.getEmail())
                    .claim("role", principal.getRole().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                    .compact();

            principal.setStatus(LOGGED_IN);
            userRepository.save(principal);
        return new UserAuthDto(
                principal.getId(),
                token,
                principal.getRole().toString());
    }
}

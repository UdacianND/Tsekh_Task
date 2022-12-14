package com.example.tsekh_task.jwt;


import com.example.tsekh_task.entity.user.User;
import com.example.tsekh_task.entity.user.UserStatus;
import com.example.tsekh_task.exception.InvalidTokenException;
import com.example.tsekh_task.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authToken.replace("Bearer ", "");

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String email = body.getSubject();
            User user = userRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
            boolean isValidToken = user.getStatus() == UserStatus.LOGGED_IN;
            if(!isValidToken)
                throw new InvalidTokenException("token "+ authToken +" is not valid");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        }catch(Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

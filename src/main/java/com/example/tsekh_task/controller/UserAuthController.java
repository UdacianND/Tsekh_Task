package com.example.tsekh_task.controller;

import com.example.tsekh_task.base_service.UserService;
import com.example.tsekh_task.jwt.JwtProvider;
import com.example.tsekh_task.payload.request.UserLoginDto;
import com.example.tsekh_task.payload.request.UserSignUpDto;
import com.example.tsekh_task.payload.response.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("register")
    public ResponseEntity<?> signUp(
            @RequestBody UserSignUpDto userSignUpDto){
        userService.registerUser(userSignUpDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginDto userDto){
        UserAuthDto userAuthDto = jwtProvider.authenticateUser(userDto);
        return new ResponseEntity<>(userAuthDto, HttpStatus.ACCEPTED);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(){
        userService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

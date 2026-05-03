package com.prod.controller;

import com.prod.dto.LoginDto;
import com.prod.dto.UserDto;
import com.prod.services.AuthService;
import com.prod.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto){

        UserDto user= authService.signUp(userDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto login){
        String token = authService.login(login);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}

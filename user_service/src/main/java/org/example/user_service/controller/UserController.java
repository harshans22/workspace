package org.example.user_service.controller;


import org.example.user_service.model.UserLoginDto;
import org.example.user_service.model.UserRegisterDto;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
        return service.register(dto)
            .map(u -> ResponseEntity.ok("User registered!"))
            .orElse(ResponseEntity.badRequest().body("User exists"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        return service.login(dto)
            .map(token -> ResponseEntity.ok().body(token))
            .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }

}

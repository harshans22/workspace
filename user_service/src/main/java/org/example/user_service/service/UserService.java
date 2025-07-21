package org.example.user_service.service;

import org.example.user_service.model.User;
import org.example.user_service.model.UserLoginDto;
import org.example.user_service.model.UserRegisterDto;
import org.example.user_service.repository.UserRepository;
import org.example.user_service.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;
    @Autowired private SecurityUtil security;

    public Optional<User> register(UserRegisterDto dto) {
        if (repo.findByEmail(dto.email).isPresent()) return Optional.empty();
        User user = new User();
        user.setUsername(dto.username);
        user.setEmail(dto.email);
        user.setPasswordHash(security.hashPassword(dto.password));
        return Optional.of(repo.save(user));
    }

    public Optional<String> login(UserLoginDto dto) {
        Optional<User> userOpt = repo.findByEmail(dto.email);
        if (userOpt.isPresent() && security.matches(dto.password, userOpt.get().getPasswordHash())) {
            return Optional.of(security.generateJwt(userOpt.get().getUsername()));
        }
        return Optional.empty();
    }
}


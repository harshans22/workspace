package org.example.user_service.service;

import org.example.user_service.Kafka.UserKafkaProducer;
import org.example.user_service.metrics.UserMetrics;
import org.example.user_service.model.User;
import org.example.user_service.model.UserLoginDto;
import org.example.user_service.model.UserRegisterDto;
import org.example.user_service.repository.UserRepository;
import org.example.user_service.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired private UserRepository repo;
    @Autowired private SecurityUtil security;
    @Autowired private UserKafkaProducer kafkaProducer;
    @Autowired private UserMetrics userMetrics;

    public Optional<User> register(UserRegisterDto dto) {
        if (repo.findByEmail(dto.email).isPresent()) return Optional.empty();
        User user = new User();
        user.setUsername(dto.username);
        user.setEmail(dto.email);
        user.setPasswordHash(security.hashPassword(dto.password));
        User saved = repo.save(user);
        kafkaProducer.sendUserCreated(saved.getUsername());
        userMetrics.incrementUserRegistration();
        log.info("User registered: {}", saved.getUsername());
        return Optional.of(saved);
    }

    public Optional<String> login(UserLoginDto dto) {
        Optional<User> userOpt = repo.findByEmail(dto.email);
        if (userOpt.isPresent() && security.matches(dto.password, userOpt.get().getPasswordHash())) {
            return Optional.of(security.generateJwt(userOpt.get().getUsername()));
        }
        return Optional.empty();
    }

    //get user details by username
    public Optional<User> getUser(String email) {
        userMetrics.incrementUserDetailsRequest();
        return repo.findByEmail(email);
    }

}


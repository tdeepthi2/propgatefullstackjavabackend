package com.example.propgate.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.User;
import com.example.propgate.repository.UserRepository;

@Service
public class AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository repo;

    public AuthService(PasswordEncoder encoder, UserRepository repo) {
        this.encoder = encoder;
        this.repo = repo;
    }

    public User register(User data) {
        if (repo.findByEmail(data.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (repo.findByUsername(data.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        data.setPassword(encoder.encode(data.getPassword()));

        if (data.getRole() == null) {
            data.setRole(User.Role.INVESTOR);
        }

        if (data.getContact() == null && data.getPhone() != null) {
            data.setContact(data.getPhone());
        }

        return repo.save(data);
    }

    public boolean verifyPassword(String email, String password) {
        User existingUser = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return encoder.matches(password, existingUser.getPassword());
    }
}
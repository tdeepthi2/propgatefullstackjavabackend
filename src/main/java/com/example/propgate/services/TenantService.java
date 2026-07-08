package com.example.propgate.services;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Tenant;
import com.example.propgate.exception.TenantNotFoundException;
import com.example.propgate.repository.TenantRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TenantService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private TenantRepository repo;

    public List<Tenant> getAllData() {
        return repo.findAll();
    }

    public Tenant saveData(Tenant data) {
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data);
    }

    public Tenant getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    public Tenant updateDatabase(Long id, Tenant data) {
        Tenant existing = repo.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));

        existing.setFullname(data.getFullname());
        existing.setContact(data.getContact());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Tenant existing = repo.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
        repo.delete(existing);
    }

    public Tenant getDelete(Long id) {
        Tenant existing = repo.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
        repo.delete(existing);
        return existing;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getKeys())
                .compact();
    }

    private Key getKeys() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Appreciation;
import com.example.propgate.exception.AppreciationNotFoundException;
import com.example.propgate.repository.AppreciationRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AppreciationService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private AppreciationRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Appreciation saveData(Appreciation data) { 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Appreciation> getAllData() {
        return repo.findAll();
    }

    public Appreciation getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new AppreciationNotFoundException("Appreciation record not found"));
    }

    public Appreciation updateDatabase(Long id, Appreciation data) {
        Appreciation existing = repo.findById(id)
                .orElseThrow(() -> new AppreciationNotFoundException("Appreciation record not found"));

        existing.setAppreciationRate(data.getAppreciationRate());
        existing.setAppreciationDate(data.getAppreciationDate());
        existing.setCurrentValue(data.getCurrentValue());
        existing.setPreviousValue(data.getPreviousValue());
        existing.setProperty(data.getProperty());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Appreciation existing = repo.findById(id)
                .orElseThrow(() -> new AppreciationNotFoundException("Appreciation record not found"));
        repo.delete(existing);
    }

    public Appreciation getDelete(Long id) {
        Appreciation existing = repo.findById(id)
                .orElseThrow(() -> new AppreciationNotFoundException("Appreciation record not found"));
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

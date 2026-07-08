package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Inquiry;
import com.example.propgate.exception.InquiryNotFoundException;
import com.example.propgate.repository.InquiryRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class InquiryService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private InquiryRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Inquiry saveData(Inquiry data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Inquiry> getAllData() {
        return repo.findAll();
    }

    public Inquiry getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("Inquiry not found"));
    }

    public Inquiry updateDatabase(Long id, Inquiry data) {
        Inquiry existing = repo.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("Inquiry not found"));

        existing.setClientName(data.getClientName());
        existing.setFullname(data.getFullname());
        existing.setContact(data.getContact());
        existing.setMessage(data.getMessage());
        existing.setStatus(data.getStatus());
        existing.setProperty(data.getProperty());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Inquiry existing = repo.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("Inquiry not found"));
        repo.delete(existing);
    }

    public Inquiry getDelete(Long id) {
        Inquiry existing = repo.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("Inquiry not found"));
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
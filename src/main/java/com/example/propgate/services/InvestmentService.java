package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Investment;
import com.example.propgate.exception.InvestmentNotFoundException;
import com.example.propgate.repository.InvestmentRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class InvestmentService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private InvestmentRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Investment saveData(Investment data) { 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Investment> getAllData() {
        return repo.findAll();
    }

    public Investment getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new InvestmentNotFoundException("Investment not found"));
    }

    public Investment updateDatabase(Long id, Investment data) {
        Investment existing = repo.findById(id)
                .orElseThrow(() -> new InvestmentNotFoundException("Investment not found"));

        existing.setInvestmentName(data.getInvestmentName());
        existing.setAmount(data.getAmount());
        existing.setInvestmentDate(data.getInvestmentDate());
        existing.setStatus(data.getStatus());
        existing.setProperty(data.getProperty());
        existing.setInvestor(data.getInvestor());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Investment existing = repo.findById(id)
                .orElseThrow(() -> new InvestmentNotFoundException("Investment not found"));
        repo.delete(existing);
    }

    public Investment getDelete(Long id) {
        Investment existing = repo.findById(id)
                .orElseThrow(() -> new InvestmentNotFoundException("Investment not found"));
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

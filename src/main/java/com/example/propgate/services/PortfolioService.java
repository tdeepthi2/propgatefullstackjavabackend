package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Portfolio;
import com.example.propgate.exception.PortfolioNotFoundException;
import com.example.propgate.repository.PortfolioRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class PortfolioService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private PortfolioRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Portfolio saveData(Portfolio data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Portfolio> getAllData() {
        return repo.findAll();
    }

    public Portfolio getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));
    }

    public Portfolio updateDatabase(Long id, Portfolio data) {
        Portfolio existing = repo.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));

        existing.setTotalInvestment(data.getTotalInvestment());
        existing.setTotalIncome(data.getTotalIncome());
        existing.setRoi(data.getRoi());
        existing.setRentalYield(data.getRentalYield());
        existing.setHealthScore(data.getHealthScore());
        existing.setOccupancyRate(data.getOccupancyRate());
        existing.setInvestor(data.getInvestor());
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Portfolio existing = repo.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));
        repo.delete(existing);
    }

    public Portfolio getDelete(Long id) {
        Portfolio existing = repo.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));
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
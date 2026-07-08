package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Expense;
import com.example.propgate.exception.ExpenseNotFoundException;
import com.example.propgate.repository.ExpenseRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class ExpenseService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Expense saveData(Expense data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Expense> getAllData() {
        return repo.findAll();
    }

    public Expense getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
    }

    public Expense updateDatabase(Long id, Expense data) {
        Expense existing = repo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        existing.setCategory(data.getCategory());
        existing.setAmount(data.getAmount());
        existing.setExpenseDate(data.getExpenseDate());
        existing.setProperty(data.getProperty());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Expense existing = repo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        repo.delete(existing);
    }

    public Expense getDelete(Long id) {
        Expense existing = repo.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
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
package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Transaction;
import com.example.propgate.exception.TransactionNotFoundException;
import com.example.propgate.repository.TransactionRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TransactionService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private TransactionRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Transaction saveData(Transaction data) { 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Transaction> getAllData() {
        return repo.findAll();
    }

    public Transaction getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    public Transaction updateDatabase(Long id, Transaction data) {
        Transaction existing = repo.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        existing.setTransactionName(data.getTransactionName());
        existing.setAmount(data.getAmount());
        existing.setTransactionDate(data.getTransactionDate());
        existing.setType(data.getType());
        existing.setStatus(data.getStatus());
        existing.setProperty(data.getProperty());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Transaction existing = repo.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        repo.delete(existing);
    }

    public Transaction getDelete(Long id) {
        Transaction existing = repo.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
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

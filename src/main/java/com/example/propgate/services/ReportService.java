package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Report;
import com.example.propgate.exception.ReportNotFoundException;
import com.example.propgate.repository.ReportRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class ReportService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private ReportRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Report saveData(Report data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Report> getAllData() {
        return repo.findAll();
    }

    public Report getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found"));
    }

    public Report updateDatabase(Long id, Report data) {
        Report existing = repo.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found"));

        existing.setReportType(data.getReportType());
        existing.setGeneratedDate(data.getGeneratedDate());
        existing.setSummary(data.getSummary());
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        Report existing = repo.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found"));
        repo.delete(existing);
    }

    public Report getDelete(Long id) {
        Report existing = repo.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found"));
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
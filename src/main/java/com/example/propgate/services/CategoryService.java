package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Category;
import com.example.propgate.exception.CategoryNotFoundException;
import com.example.propgate.repository.CategoryRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class CategoryService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private CategoryRepository repo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public Category saveData(Category data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return repo.save(data); 
    }

    public List<Category> getAllData() {
        return repo.findAll();
    }

    public Category getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public Category updateDatabase(Long id, Category data) {
        Category existing = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        existing.setName(data.getName());
        existing.setDescription(data.getDescription());

        return repo.save(existing);
    }

   /*  public void deleteData(Long id) {
        Category existing = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        repo.delete(existing);
    }
        */

    public Category getDelete(Long id) {
        Category existing = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
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
package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.Property;
import com.example.propgate.exception.PropertyNotFoundException;
import com.example.propgate.repository.PropertyRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key; 
import java.util.Date; 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PropertyService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyRepository repo;

    public List<Property> getAllData() {
        return propertyRepository.findAll();
    }

    public Property saveData(Property data) {
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        return propertyRepository.save(data);
    }

    public Property getUserDetails(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
    }

    public Property updateDatabase(Long id, Property data) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        existing.setPropertyName(data.getPropertyName());
        existing.setLocation(data.getLocation());
        existing.setType(data.getType());
        existing.setPurchasePrice(data.getPurchasePrice());
        existing.setCurrentValue(data.getCurrentValue());
        existing.setRentalIncome(data.getRentalIncome());
        existing.setAppreciationRate(data.getAppreciationRate());
        existing.setStatus(data.getStatus());
        existing.setOwner(data.getOwner());

        return propertyRepository.save(existing);
    }

    public void deleteData(Long id) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));

        propertyRepository.delete(existing);
    }

    public Property getDelete(Long id) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        propertyRepository.delete(existing);
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

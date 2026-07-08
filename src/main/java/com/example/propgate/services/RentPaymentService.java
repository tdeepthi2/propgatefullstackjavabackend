package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.RentPayment;
import com.example.propgate.exception.RentPaymentNotFoundException;
import com.example.propgate.repository.RentPaymentRepository;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class RentPaymentService {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.exp}")
    private long exp;

    @Autowired
    private RentPaymentRepository repo;

    @Autowired
    private com.example.propgate.repository.TenantRepository tenantRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public RentPayment saveData(RentPayment data){ 
        if (data.getPassword() != null) {
            String Bcrypass = passwordEncoder.encode(data.getPassword());
            data.setPassword(Bcrypass);
        }
        if (data.getTenant() != null) {
            Long tenantId = data.getTenant().getId();
            if (tenantId != null) {
                com.example.propgate.entity.Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
                if (tenant == null) {
                    tenant = new com.example.propgate.entity.Tenant();
                    tenant.setId(tenantId);
                    tenant.setFullname(data.getTenantname() != null ? data.getTenantname() : "Unknown");
                    tenant.setContact("Not Specified");
                    tenantRepository.save(tenant);
                }
                data.setTenant(tenant);
            }
        }
        return repo.save(data); 
    }

    public List<RentPayment> getAllData() {
        return repo.findAll();
    }

    public RentPayment getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RentPaymentNotFoundException("Rent payment not found"));
    }

    public RentPayment updateDatabase(Long id, RentPayment data) {
        RentPayment existing = repo.findById(id)
                .orElseThrow(() -> new RentPaymentNotFoundException("Rent payment not found"));

        //existing.setTenant(data.getTenant());
        existing.setTenantname(data.getTenantname());
        existing.setAmount(data.getAmount());
        existing.setPaymentDate(data.getPaymentDate());
        existing.setDueDate(data.getDueDate());
        existing.setStatus(data.getStatus());
        existing.setDate(data.getDate());

        return repo.save(existing);
    }

    public void deleteData(Long id) {
        RentPayment existing = repo.findById(id)
                .orElseThrow(() -> new RentPaymentNotFoundException("Rent payment not found"));
        repo.delete(existing);
    }

    public RentPayment getDelete(Long id) {
        RentPayment existing = repo.findById(id)
                .orElseThrow(() -> new RentPaymentNotFoundException("Rent payment not found"));
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
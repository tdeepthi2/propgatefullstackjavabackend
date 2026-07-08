package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Portfolio;
import com.example.propgate.services.PortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService service;

    @PostMapping("/postData")
    public Portfolio saveData(@RequestBody Portfolio data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Portfolio> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Portfolio not found");
        }
    }

    @PutMapping("/update/{id}")
    public Portfolio update(@PathVariable Long id, @RequestBody Portfolio data) {
        return service.updateDatabase(id, data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getDelete(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Portfolio not found");
        }
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @PostMapping("/get/gmail")
    public String generateToken(@RequestParam("mail") String gmail) {
        return service.generateToken(gmail);
    }
}

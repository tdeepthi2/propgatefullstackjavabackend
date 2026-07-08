package com.example.propgate.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Transaction;
import com.example.propgate.services.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/postData")
    public Transaction saveData(@RequestBody Transaction data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Transaction> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Transaction not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Transaction data) {
        try {
            return ResponseEntity.ok(service.updateDatabase(id, data));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Transaction not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getDelete(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Transaction not found");
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

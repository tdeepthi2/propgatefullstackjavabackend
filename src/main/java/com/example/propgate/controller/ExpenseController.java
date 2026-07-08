package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Expense;
import com.example.propgate.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenses;

    @PostMapping("/postData")
    public Expense saveData(@RequestBody Expense data) {
        return expenses.saveData(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public List<Expense> getData() {
        return expenses.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        try {
            Expense getData = expenses.getUserDetails(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found with id: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @RequestBody Expense data) {
        try {
            Expense updated = expenses.updateDatabase(id, data);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found with id: " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        try {
            Expense deleted = expenses.getDelete(id);
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found with id: " + id);
        }
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping("/get/gmail")
    public String generateToken(@RequestParam("mail") String gmail) {
        return expenses.generateToken(gmail);
    }
}

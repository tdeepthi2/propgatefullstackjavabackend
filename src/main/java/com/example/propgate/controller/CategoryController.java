package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Category;
import com.example.propgate.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/postData")
    public Category saveData(@RequestBody Category data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Category not found");
        }
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category data) {
        return service.updateDatabase(id, data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getDelete(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Category not found");
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

package com.example.propgate.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Appreciation;
import com.example.propgate.services.AppreciationService;

@RestController
@RequestMapping("/appreciation")
public class AppreciationController {

    @Autowired
    private AppreciationService service;

    @PostMapping("/postData")
    public Appreciation saveData(@RequestBody Appreciation data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Appreciation> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Appreciation record not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Appreciation data) {
        try {
            return ResponseEntity.ok(service.updateDatabase(id, data));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Appreciation record not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getDelete(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Appreciation record not found");
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

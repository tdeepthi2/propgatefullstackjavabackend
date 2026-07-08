package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Tenant;
import com.example.propgate.services.TenantService;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService service;

    @PostMapping("/postData")
    public Tenant saveData(@RequestBody Tenant data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Tenant> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Tenant not found");
        }
    }

    @PutMapping("/update/{id}")
    public Tenant update(@PathVariable Long id, @RequestBody Tenant data) {
        return service.updateDatabase(id, data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteData(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Tenant not found");
        }
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}

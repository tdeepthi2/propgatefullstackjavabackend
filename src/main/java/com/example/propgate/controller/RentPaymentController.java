package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.RentPayment;
import com.example.propgate.services.RentPaymentService;

@RestController
@RequestMapping("/rentpayment")
public class RentPaymentController {

    @Autowired
    private RentPaymentService service;

    @PostMapping("/postData")
    public RentPayment saveData(@RequestBody RentPayment data) {
        return service.saveData(data);
    }

    @GetMapping("/getAll")
    public List<RentPayment> getAll() {
        return service.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Rent payment not found");
        }
    }

    @PutMapping("/update/{id}")
    public RentPayment update(@PathVariable Long id, @RequestBody RentPayment data) {
        return service.updateDatabase(id, data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getDelete(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Rent payment not found");
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

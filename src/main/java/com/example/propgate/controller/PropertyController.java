package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Property;
import com.example.propgate.services.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertys;

    @PostMapping("/postData")
    public Property saveData(@RequestBody Property data) {
        return propertys.saveData(data);
    }

    @GetMapping("/getAll")
    public List<Property> getData() {
        return propertys.getAllData();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        try {
            Property getData = propertys.getUserDetails(id);
            return ResponseEntity.ok(getData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found with id: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @RequestBody Property data) {
        try {
            Property updated = propertys.updateDatabase(id, data);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found with id: " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> getDeleteData(@PathVariable Long id) {
        try {
            Property deleted = propertys.getDelete(id);
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found with id: " + id);
        }
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @PostMapping("/get/gmail")
    public String generateToken(@RequestParam("mail") String gmail) {
        return propertys.generateToken(gmail);
    }
}

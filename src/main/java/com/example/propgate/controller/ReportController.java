package com.example.propgate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.Report;
import com.example.propgate.services.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService ser;

    @PostMapping("/postData")
    public Report postData(@RequestBody Report r) {
        return ser.saveData(r);
    }

    @GetMapping("/getAllData")
    public List<Report> getAllData() {
        return ser.getAllData();
    }

    @GetMapping("/getById/{id}")
    public Report getById(@PathVariable Long id) {
        return ser.getUserDetails(id);
    }

    @PutMapping("/updateData/{id}")
    public Report updateData(@PathVariable Long id, @RequestBody Report r) {
        return ser.updateDatabase(id, r);
    }

    @DeleteMapping("/deleteData/{id}")
    public Report deleteData(@PathVariable Long id) {
        return ser.getDelete(id);
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @PostMapping("/get/gmail")
    public String generateToken(@RequestParam("mail") String gmail) {
        return ser.generateToken(gmail);
    }
}

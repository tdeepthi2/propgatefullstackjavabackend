package com.example.propgate.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "appreciations")
public class Appreciation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal appreciationRate;
    private LocalDate appreciationDate;
    private BigDecimal currentValue;
    private BigDecimal previousValue;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private String password;

    public Appreciation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAppreciationRate() {
        return appreciationRate;
    }

    public void setAppreciationRate(BigDecimal appreciationRate) {
        this.appreciationRate = appreciationRate;
    }

    public LocalDate getAppreciationDate() {
        return appreciationDate;
    }

    public void setAppreciationDate(LocalDate appreciationDate) {
        this.appreciationDate = appreciationDate;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(BigDecimal previousValue) {
        this.previousValue = previousValue;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.propgate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Username should not be empty")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "Password should not be empty")
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Single canonical field — stored as "fullname" column for legacy compatibility
    @Column(name = "fullname")
    @NotBlank(message = "Fullname should not be empty")
    private String fullname;


    @Column(unique = true)
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    // Single canonical field — stored as "phone" column; contact is an alias
    private String phone;

    @Column(name = "contact")
    private String contact;

    private String status;

    public enum Role {

        ADMIN,
        INVESTOR,
        AGENT
    }

    public User() {
    }

    public User(String username, String email, String password, Role role, String fullname, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.phone = phone;
        this.contact = phone;
    }

    public User(Long id, String fullname, String email, String password, String contact, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.phone = contact;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Both getFullName() and getFullname() return the same field
    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.contact = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
        this.phone = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
// ROLE_ADMIN,
// ROLE_INVESTOR,
// ROLE_AGENT
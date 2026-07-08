package com.example.propgate.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.propgate.entity.User;
import com.example.propgate.exception.UserNotFoundException;
import com.example.propgate.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveData(User data) {
        String Bcrypass = passwordEncoder.encode(data.getPassword());
        data.setPassword(Bcrypass);
        return repo.save(data);
    }

    public List<User> getAllData() {
        return repo.findAll();
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserDetails(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User updateDatabase(Long id, User data) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existing.setFullname(data.getFullname());
        existing.setEmail(data.getEmail());
        existing.setContact(data.getContact());
        existing.setUsername(data.getUsername());
        existing.setStatus(data.getStatus());
        existing.setPhone(data.getPhone());

        existing.setPassword(passwordEncoder.encode(data.getPassword()));

        if (data.getRole() != null) {
            existing.setRole(data.getRole());
        }

        return repo.save(existing);
    }

    public String deleteData(Long id) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        repo.delete(existing);
        return "User deleted successfully";
    }
}

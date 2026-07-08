/*package com.example.propgate.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.User;
import com.example.propgate.services.AuthService;
import com.example.propgate.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            AuthService authService) {

        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    // Register User
    @PostMapping("/register")
    public User register(@RequestBody User data) {
        return authService.register(data);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User data) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getEmail(),
                            data.getPassword()));

            String token = jwtService.generateToken(data.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Successful");
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid Email or Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
    */



package com.example.propgate.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.propgate.entity.User;
import com.example.propgate.services.AuthService;
import com.example.propgate.services.JwtService;
import com.example.propgate.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            AuthService authService,
            UserRepository userRepository) {

        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // Register User
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {

            User registeredUser = authService.register(user);

            return ResponseEntity.ok(registeredUser);

        } catch (Exception e) {

             e.printStackTrace();   // Add this line

            Map<String, String> error = new HashMap<>();

            if (e.getMessage() != null &&
                (e.getMessage().toLowerCase().contains("email already exists") ||
                 e.getMessage().toLowerCase().contains("username already exists"))) {

                error.put("message", e.getMessage());

            } else {

                error.put("message", "Error occurred while registering user");

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()));

            String token = jwtService.generateToken(user.getEmail());

            User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
            String role = (existingUser != null) ? existingUser.getRole().name() : null;

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Successful");
            response.put("token", token);
            response.put("role", role);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid Email or Password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
}
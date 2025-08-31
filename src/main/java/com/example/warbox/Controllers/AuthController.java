package com.example.warbox.Controllers;

import com.example.warbox.Dto.AuthResponse;
import com.example.warbox.Dto.LoginRequest;
import com.example.warbox.Dto.RegisterRequest;
import com.example.warbox.Repositorys.AppUserRepo;
import com.example.warbox.Utils.JwtUtil;
import com.example.warbox.Utils.PasswordUtil;
import com.example.warbox.models.AppUserEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AppUserRepo userRepo;

    public AuthController(AppUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){

        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if(userRepo.findByHandle(request.getHandle()).isPresent()){
            return ResponseEntity.badRequest().body("Handle already exists");
        }

        AppUserEntity user = new AppUserEntity();

        user.setEmail(request.getEmail());
        user.setHandle(request.getHandle());
        user.setDisplayName(request.getDisplayName());
        user.setPasswordHash(PasswordUtil.hash(request.getPassword()));

        AppUserEntity savedUser = userRepo.save(user);

        String token = JwtUtil.generateToken(savedUser.getId(),savedUser.getEmail(),savedUser.getHandle());

        AuthResponse response = new AuthResponse(token, savedUser.getEmail(), savedUser.getHandle(), savedUser.getDisplayName());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Optional<AppUserEntity> userOptional = userRepo.findByEmail(request.getEmail());

        if(userOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Email or password is incorrect");
        }


        AppUserEntity user = userOptional.get();

        if(!PasswordUtil.matches(request.getPassword(), user.getPasswordHash())){
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = JwtUtil.generateToken(user.getId(),user.getEmail(),user.getHandle());

        AuthResponse response = new AuthResponse(token,user.getEmail(),user.getHandle(),user.getDisplayName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String authHeader) {

        // Extract token from "Bearer eyJhbGciOiJIUzUxMiJ9..."
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Test if token is valid
        if (!JwtUtil.isTokenValid(token)) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        // Extract user ID from token
        UUID userId = JwtUtil.getUserIdFromToken(token);

        return ResponseEntity.ok("Token is valid! User ID: " + userId);
    }



}

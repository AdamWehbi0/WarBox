package com.example.warbox.Controllers;

import com.example.warbox.Dto.LoginRequest;
import com.example.warbox.Dto.RegisterRequest;
import com.example.warbox.Repositorys.AppUserRepo;
import com.example.warbox.Utils.PasswordUtil;
import com.example.warbox.models.AppUserEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        return ResponseEntity.ok("User registered successfully: " + savedUser.getHandle());
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

        return ResponseEntity.ok("Login successful for: " + user.getHandle());
    }



}

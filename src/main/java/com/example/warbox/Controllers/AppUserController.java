package com.example.warbox.Controllers;
import com.example.warbox.Repositorys.AppUserRepo;
import com.example.warbox.models.AppUserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserRepo users;

    public AppUserController(AppUserRepo users) {
        this.users = users;
    }

    // Get all users (debug only, remove later)
    @GetMapping
    public List<AppUserEntity> all() {
        return users.findAll();
    }

    // Get a user by id
    @GetMapping("/{id}")
    public AppUserEntity get(@PathVariable UUID id) {
        return users.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // Update user profile (displayName or handle)
    @PatchMapping("/{id}")
    public AppUserEntity update(@PathVariable UUID id,
                          @RequestParam(required = false) String handle,
                          @RequestParam(required = false) String displayName) {
        AppUserEntity user = users.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (handle != null) user.setHandle(handle);
        if (displayName != null) user.setDisplayName(displayName);

        return users.save(user);
    }
}

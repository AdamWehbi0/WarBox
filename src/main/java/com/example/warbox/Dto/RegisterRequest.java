package com.example.warbox.Dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain uppercase, lowercase, and number")
    private String password;

    @NotBlank(message = "Handle is required")
    @Size(min = 3, max = 20, message = "Handle must be 3-20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Handle can only contain letters, numbers, and underscores")
    private String handle;

    private String displayName; // optional

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHandle() { return handle; }
    public void setHandle(String handle) { this.handle = handle; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
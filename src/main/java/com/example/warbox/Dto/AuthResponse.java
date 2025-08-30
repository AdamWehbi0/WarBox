package com.example.warbox.Dto;

public class AuthResponse {

    private String token;
    private String email;
    private String handle;
    private String displayName;

    public AuthResponse(String token, String email, String handle, String displayName) {
        this.token = token;
        this.email = email;
        this.handle = handle;
        this.displayName = displayName;
    }

    // Getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getHandle() { return handle; }
    public String getDisplayName() { return displayName; }
}
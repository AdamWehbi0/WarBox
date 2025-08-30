package com.example.warbox.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String rawPassword){
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String storedHash){
        return encoder.matches(rawPassword, storedHash);
    }
}

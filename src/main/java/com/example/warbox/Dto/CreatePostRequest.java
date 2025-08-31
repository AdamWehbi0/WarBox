package com.example.warbox.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {

    @NotBlank(message = "Post content cannot be empty")
    @Size(max = 500, message = "Post cannot exceed 500 characters")
    private String content;

    // Constructor
    public CreatePostRequest() {}

    // Getter and setter
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
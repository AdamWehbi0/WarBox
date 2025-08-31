package com.example.warbox.Dto;

import com.example.warbox.models.PostEntity;
import java.time.Instant;
import java.util.UUID;

public class PostResponse {

    private UUID id;
    private String content;
    private String authorHandle;
    private String authorDisplayName;
    private Instant createdAt;

    // Constructor from PostEntity
    public PostResponse(PostEntity post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.authorHandle = post.getAuthor().getHandle();
        this.authorDisplayName = post.getAuthor().getDisplayName();
        this.createdAt = post.getCreatedAt();
    }

    // Getters
    public UUID getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorHandle() { return authorHandle; }
    public String getAuthorDisplayName() { return authorDisplayName; }
    public Instant getCreatedAt() { return createdAt; }
}
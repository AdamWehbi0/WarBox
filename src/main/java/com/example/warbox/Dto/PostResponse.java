package com.example.warbox.Dto;

import com.example.warbox.models.PostEntity;
import java.time.Instant;
import java.util.UUID;

public class PostResponse {

    private Long likeCount;
    private Boolean likedByCurrentUser;
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

    public PostResponse(PostEntity post, Long likeCount, Boolean likedByCurrentUser) {
        this.id = post.getId();
        this.content = post.getContent();
        this.authorHandle = post.getAuthor().getHandle();
        this.authorDisplayName = post.getAuthor().getDisplayName();
        this.createdAt = post.getCreatedAt();
        this.likeCount = likeCount;
        this.likedByCurrentUser = likedByCurrentUser;
    }

    // Getters
    public UUID getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorHandle() { return authorHandle; }
    public String getAuthorDisplayName() { return authorDisplayName; }
    public Instant getCreatedAt() { return createdAt; }
    public void setLikeCount(Long likeCount) { this.likeCount = likeCount; }
    public void setLikedByCurrentUser(Boolean likedByCurrentUser) { this.likedByCurrentUser = likedByCurrentUser; }
}
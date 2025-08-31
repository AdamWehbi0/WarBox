package com.example.warbox.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 500) // Limit posts to 500 characters
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AppUserEntity author;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    // Constructors
    public PostEntity() {}

    public PostEntity(String content, AppUserEntity author) {
        this.content = content;
        this.author = author;
    }

    // Getters and setters
    public UUID getId() { return id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public AppUserEntity getAuthor() { return author; }
    public void setAuthor(AppUserEntity author) { this.author = author; }

    public Instant getCreatedAt() { return createdAt; }
}
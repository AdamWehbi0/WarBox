package com.example.warbox.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "post_like",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class LikeEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public LikeEntity() {}

    public LikeEntity(AppUserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }

    public UUID getId() { return id; }

    public AppUserEntity getUser() { return user; }
    public void setUser(AppUserEntity user) { this.user = user; }

    public PostEntity getPost() { return post; }
    public void setPost(PostEntity post) { this.post = post; }

    public Instant getCreatedAt() { return createdAt; }

}

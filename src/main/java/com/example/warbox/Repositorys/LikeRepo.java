package com.example.warbox.Repositorys;

import com.example.warbox.models.LikeEntity;
import com.example.warbox.models.PostEntity;
import com.example.warbox.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepo extends JpaRepository<LikeEntity, UUID> {

    // Find a specific like (to check if user already liked a post)
    Optional<LikeEntity> findByUserAndPost(AppUserEntity user, PostEntity post);

    // Count likes for a specific post
    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.post = :post")
    Long countByPost(@Param("post") PostEntity post);

    // Get all users who liked a specific post
    @Query("SELECT l.user FROM LikeEntity l WHERE l.post = :post ORDER BY l.createdAt DESC")
    List<AppUserEntity> findUsersByPost(@Param("post") PostEntity post);

    // Delete a like (for unliking)
    void deleteByUserAndPost(AppUserEntity user, PostEntity post);
}
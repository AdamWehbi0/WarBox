package com.example.warbox.Repositorys;

import com.example.warbox.models.PostEntity;
import com.example.warbox.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepo extends JpaRepository<PostEntity, UUID> {

    // Get all posts by a specific user, ordered by newest first
    List<PostEntity> findByAuthorOrderByCreatedAtDesc(AppUserEntity author);

    // Get all posts ordered by newest first (for the main feed)
    List<PostEntity> findAllByOrderByCreatedAtDesc();

    // Get recent posts with a limit (for pagination later)
    @Query("SELECT p FROM PostEntity p ORDER BY p.createdAt DESC")
    List<PostEntity> findRecentPosts();
}
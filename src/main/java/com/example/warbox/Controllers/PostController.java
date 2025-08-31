package com.example.warbox.Controllers;

import com.example.warbox.Dto.CreatePostRequest;
import com.example.warbox.Dto.PostResponse;
import com.example.warbox.Repositorys.AppUserRepo;
import com.example.warbox.Repositorys.PostRepo;
import com.example.warbox.Utils.JwtUtil;
import com.example.warbox.models.AppUserEntity;
import com.example.warbox.models.PostEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final AppUserRepo userRepo;
    private final PostRepo postRepo;

    public PostController(AppUserRepo userRepo, PostRepo postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String authHeader,
                                        @Valid @RequestBody CreatePostRequest request) {
        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Invalid authorization header");
        }

        String token = authHeader.substring(7);
        if(!JwtUtil.isTokenValid(token)){
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        UUID userId = JwtUtil.getUserIdFromToken(token);
        AppUserEntity author = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        PostEntity post = new PostEntity(request.getContent(), author);
        PostEntity savedPost = postRepo.save(post);

        return ResponseEntity.ok(new PostResponse(savedPost));
    }
}

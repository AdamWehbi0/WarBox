package com.example.warbox.Controllers;

import com.example.warbox.Dto.CreatePostRequest;
import com.example.warbox.Dto.PostResponse;
import com.example.warbox.Repositorys.AppUserRepo;
import com.example.warbox.Repositorys.LikeRepo;
import com.example.warbox.Repositorys.PostRepo;
import com.example.warbox.Utils.JwtUtil;
import com.example.warbox.models.AppUserEntity;
import com.example.warbox.models.LikeEntity;
import com.example.warbox.models.PostEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final AppUserRepo userRepo;
    private final PostRepo postRepo;
    private final LikeRepo likeRepo;

    public PostController(AppUserRepo userRepo, PostRepo postRepo, LikeRepo likeRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.likeRepo = likeRepo;
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

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postRepo.findAllByOrderByCreatedAtDesc().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/user/{handle}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String handle) {
        AppUserEntity user = userRepo.findByHandle(handle)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<PostResponse> posts = postRepo.findByAuthorOrderByCreatedAtDesc(user)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable UUID postId, @RequestHeader("Authorization") String authHeader) {

        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Invalid authorization header");
        }

        String token = authHeader.substring(7);
        if(!JwtUtil.isTokenValid(token)){
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        UUID userId = JwtUtil.getUserIdFromToken(token);
        AppUserEntity user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postRepo.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if(likeRepo.findByUserAndPost(user,post).isPresent()) {
            return ResponseEntity.badRequest().body("Post already liked");
        }

        LikeEntity like = new LikeEntity(user,post);

        likeRepo.save(like);

        Long likeCount = likeRepo.countByPost(post);
        return ResponseEntity.ok("Post liked! Total likes: " + likeCount);
    }

    @DeleteMapping("/{postId}/like")
    @Transactional
    public ResponseEntity<?> deleteLikePost(@PathVariable UUID postId, @RequestHeader("Authorization") String authHeader) {

        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Invalid authorization header");
        }

        String token = authHeader.substring(7);
        if(!JwtUtil.isTokenValid(token)){
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        UUID userId = JwtUtil.getUserIdFromToken(token);
        AppUserEntity user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postRepo.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if(likeRepo.findByUserAndPost(user,post).isEmpty()){
            return ResponseEntity.badRequest().body("Post not liked");
        }

        likeRepo.deleteByUserAndPost(user,post);

        Long likeCount = likeRepo.countByPost(post);

        return ResponseEntity.ok("Post unliked! Total likes: " + likeCount);

    }

}

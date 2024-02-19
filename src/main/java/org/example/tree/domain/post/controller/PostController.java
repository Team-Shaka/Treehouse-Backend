package org.example.tree.domain.post.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.dto.PostRequestDTO;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.service.PostService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/trees/{treeId}/feed/posts")
    public ApiResponse<PostResponseDTO.createPost> createPost(
            @PathVariable final Long treeId,
            @RequestHeader("Authorization") final String header,
            @RequestPart final PostRequestDTO.createPost request,
            @RequestPart(required = false) List<MultipartFile> images
    ) throws Exception {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(postService.createPost(treeId, request, images, token));
    }

    @GetMapping("/trees/{treeId}/feed")
    public ApiResponse<List<PostResponseDTO.getFeed>> getFeed(
            @PathVariable final Long treeId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(postService.getFeed(treeId, token));
    }

    @GetMapping("/trees/{treeId}/feed/posts/{postId}")
    public ApiResponse<PostResponseDTO.getPost> getPost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(postService.getPost(treeId, postId, token));
    }

    @GetMapping("/trees/{treeId}/feed/posts")
    public ApiResponse<List<PostResponseDTO.getPost>> getPosts(
            @PathVariable final Long treeId,
            @RequestParam(name = "member") final Long profileId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(postService.getTreePosts(treeId, profileId, token));
    }

    @PatchMapping("/trees/{treeId}/feed/posts/{postId}")
    public ApiResponse updatePost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header,
            @RequestBody final PostRequestDTO.updatePost request
    ) {
        String token = header.replace("Bearer ", "");
        postService.updatePost(treeId, postId, request, token);
        return ApiResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/posts/{postId}")
    public ApiResponse deletePost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        postService.deletePost(treeId, postId, token);
        return ApiResponse.onSuccess("");
    }
}

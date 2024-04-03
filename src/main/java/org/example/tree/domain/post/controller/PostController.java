package org.example.tree.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.post.dto.PostRequestDTO;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.service.PostService;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/trees/{treeId}/feed/posts")
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    public ApiResponse<PostResponseDTO.createPost> createPost(
            @PathVariable final Long treeId,
            @RequestBody final PostRequestDTO.createPost request,
            @AuthMember @Parameter(hidden = true)  Member member
    ) throws Exception {
        return ApiResponse.onSuccess(postService.createPost(treeId, request, member));
    }

    @GetMapping("/trees/{treeId}/feed")
    @Operation(summary = "피드 조회", description = "특정 트리하우스 속 피드를 불러옵니다.")
    public ApiResponse<PostResponseDTO.getFeed> getFeed(
            @PathVariable final Long treeId,
            @AuthMember @Parameter(hidden = true)  Member member
    ) {
        return ApiResponse.onSuccess(postService.getFeed(treeId, member));
    }

    @GetMapping("/trees/{treeId}/feed/posts/{postId}")
    @Operation(summary = "게시글 조회", description = "특정 게시글을 조회합니다.")
    public ApiResponse<PostResponseDTO.getPost> getPost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @AuthMember @Parameter(hidden = true)  Member member
    )
    {
        return ApiResponse.onSuccess(postService.getPost(treeId, postId, member));
    }

    @GetMapping("/trees/{treeId}/feed/posts")
    @Operation(summary = "특정 멤버의 게시글 조회", description = "특정 멤버가 작성한 게시글 목록을 불러옵니다.")
    public ApiResponse<List<PostResponseDTO.getPost>> getPosts(
            @PathVariable final Long treeId,
            @RequestParam(name = "member") final Long profileId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return ApiResponse.onSuccess(postService.getTreePosts(treeId, profileId, member));
    }

    @PatchMapping("/trees/{treeId}/feed/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    public ApiResponse updatePost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @AuthMember @Parameter(hidden = true) Member member,
            @RequestBody final PostRequestDTO.updatePost request
    ) {
        postService.updatePost(treeId, postId, request, member);
        return ApiResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/posts/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ApiResponse deletePost(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        postService.deletePost(treeId, postId, member);
        return ApiResponse.onSuccess("");
    }
}

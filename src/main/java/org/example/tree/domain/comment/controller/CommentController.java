package org.example.tree.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.dto.CommentRequestDTO;
import org.example.tree.domain.comment.dto.CommentResponseDTO;
import org.example.tree.domain.comment.service.CommentService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/trees/{treeId}/feed/posts/{postId}/comments")
    public ApiResponse createComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header,
            @RequestBody final CommentRequestDTO.createComment request

    ) {
        String token = header.replace("Bearer ", "");
        commentService.createComment(treeId, postId, request, token);
        return ApiResponse.onSuccess("");
    }

    @GetMapping("/trees/{treeId}/feed/posts/{postId}/comments")
    public ApiResponse<List<CommentResponseDTO.getComment>> getComments(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(commentService.getComments(treeId, postId, token));
    }

    @PatchMapping("/trees/{treeId}/feed/posts/{postId}/comments/{commentId}")
    public ApiResponse updateComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @PathVariable final Long commentId,
            @RequestHeader("Authorization") final String header,
            @RequestBody final CommentRequestDTO.updateComment request
    ) {
        String token = header.replace("Bearer ", "");
        commentService.updateComment(treeId, postId, commentId, request, token);
        return ApiResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/posts/{postId}/comments/{commentId}")
    public ApiResponse deleteComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @PathVariable final Long commentId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        commentService.deleteComment(treeId, postId, commentId, token);
        return ApiResponse.onSuccess("");
    }
}

package org.example.tree.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글 작성합니다.")
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
    @Operation(summary = "댓글 조회", description = "Reaction과 댓글에 달린 답글도 같이 출력됩니다.")
    public ApiResponse<List<CommentResponseDTO.getComment>> getComments(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(commentService.getComments(treeId, postId, token));
    }

    @PatchMapping("/trees/{treeId}/feed/posts/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
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
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
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

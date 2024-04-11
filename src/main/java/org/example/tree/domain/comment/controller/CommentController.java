package org.example.tree.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.dto.CommentRequestDTO;
import org.example.tree.domain.comment.dto.CommentResponseDTO;
import org.example.tree.domain.comment.service.CommentService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/trees/{treeId}/feed/posts/{postId}/comments")
    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글 작성합니다.")
    public CommonResponse createComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @RequestBody final CommentRequestDTO.createComment request,
            @AuthMember @Parameter(hidden = true) Member member

            ) {
        commentService.createComment(treeId, postId, request, member);
        return CommonResponse.onSuccess("");
    }

    @GetMapping("/trees/{treeId}/feed/posts/{postId}/comments")
    @Operation(summary = "댓글 조회", description = "Reaction과 댓글에 달린 답글도 같이 출력됩니다.")
    public CommonResponse<List<CommentResponseDTO.getComment>> getComments(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return CommonResponse.onSuccess(commentService.getComments(treeId, postId, member));
    }

    @PatchMapping("/trees/{treeId}/feed/posts/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public CommonResponse updateComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @PathVariable final Long commentId,
            @RequestBody final CommentRequestDTO.updateComment request,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        commentService.updateComment(treeId, postId, commentId, request, member);
        return CommonResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/posts/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public CommonResponse deleteComment(
            @PathVariable final Long treeId,
            @PathVariable final Long postId,
            @PathVariable final Long commentId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        commentService.deleteComment(treeId, postId, commentId, member);
        return CommonResponse.onSuccess("");
    }
}

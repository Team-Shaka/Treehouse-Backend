package org.example.tree.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.dto.ReplyRequestDTO;
import org.example.tree.domain.comment.service.ReplyService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/trees/{treeId}/feed/comments/{commentId}/reply")
    @Operation(summary = "답글 작성", description = "특정 댓글에 답글을 작성합니다.")
    public ApiResponse createReply(
            @PathVariable final Long treeId,
            @PathVariable final Long commentId,
            @RequestBody final ReplyRequestDTO.createReply request,
            @AuthMember @Parameter(hidden = true) Member member
            ) {
        replyService.createReply(treeId, commentId, request, member);
        return ApiResponse.onSuccess("");
    }

    @PatchMapping("/trees/{treeId}/feed/comments/{commentId}/reply/{replyId}")
    @Operation(summary = "답글 수정", description = "답글을 수정합니다.")
    public ApiResponse updateReply(
            @PathVariable final Long treeId,
            @PathVariable final Long commentId,
            @PathVariable final Long replyId,
            @RequestBody final ReplyRequestDTO.updateReply request,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        replyService.updateReply(treeId, commentId, replyId, request, member);
        return ApiResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/comments/{commentId}/reply/{replyId}")
    @Operation(summary = "답글 삭제", description = "답글을 삭제합니다.")
    public ApiResponse deleteReply(
            @PathVariable final Long treeId,
            @PathVariable final Long commentId,
            @PathVariable final Long replyId,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        replyService.deleteReply(treeId, commentId, replyId, member);
        return ApiResponse.onSuccess("");
    }

}

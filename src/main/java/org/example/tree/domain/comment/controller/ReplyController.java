package org.example.tree.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.dto.ReplyRequestDTO;
import org.example.tree.domain.comment.service.ReplyService;
import org.example.tree.global.common.ApiResponse;
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
            @RequestHeader("Authorization") final String header,
            @RequestBody final ReplyRequestDTO.createReply request
    ) {
        String token = header.replace("Bearer ", "");
        replyService.createReply(treeId, commentId, request, token);
        return ApiResponse.onSuccess("");
    }

    @PatchMapping("/trees/{treeId}/feed/comments/{commentId}/reply/{replyId}")
    @Operation(summary = "답글 수정", description = "답글을 수정합니다.")
    public ApiResponse updateReply(
            @PathVariable final Long treeId,
            @PathVariable final Long commentId,
            @PathVariable final Long replyId,
            @RequestHeader("Authorization") final String header,
            @RequestBody final ReplyRequestDTO.updateReply request
    ) {
        String token = header.replace("Bearer ", "");
        replyService.updateReply(treeId, commentId, replyId, request, token);
        return ApiResponse.onSuccess("");
    }

    @DeleteMapping("/trees/{treeId}/feed/comments/{commentId}/reply/{replyId}")
    @Operation(summary = "답글 삭제", description = "답글을 삭제합니다.")
    public ApiResponse deleteReply(
            @PathVariable final Long treeId,
            @PathVariable final Long commentId,
            @PathVariable final Long replyId,
            @RequestHeader("Authorization") final String header
    ) {
        String token = header.replace("Bearer ", "");
        replyService.deleteReply(treeId, commentId, replyId, token);
        return ApiResponse.onSuccess("");
    }

}

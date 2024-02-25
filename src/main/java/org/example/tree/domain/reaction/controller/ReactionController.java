package org.example.tree.domain.reaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.reaction.dto.ReactionRequestDTO;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.service.ReactionService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping("/trees/{treeId}/feed/posts/{postId}/reaction")
    @Operation(summary = "게시글 리액션", description = "게시글에 리액션을 추가합니다.")
    public ApiResponse<ReactionResponseDTO.addReaction> createPostReaction(
            @PathVariable Long treeId,
            @PathVariable Long postId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
            ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(reactionService.reactToPost(treeId, postId, request, token));
    }

    @PostMapping("/trees/{treeId}/feed/comments/{commentId}/reaction")
    @Operation(summary = "댓글 리액션", description = "댓글에 리액션을 추가합니다.")
    public ApiResponse<ReactionResponseDTO.addReaction> createCommentReaction(
            @PathVariable Long treeId,
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(reactionService.reactToComment(treeId, commentId, request, token));
    }

    @PostMapping("/trees/{treeId}/feed/replies/{replyId}/reaction")
    @Operation(summary = "답글 리액션", description = "답글에 리액션을 추가합니다.")
    public ApiResponse<ReactionResponseDTO.addReaction> createReplyReaction(
            @PathVariable Long treeId,
            @PathVariable Long replyId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
    ) {
        String token = header.replace("Bearer ", "");
        return ApiResponse.onSuccess(reactionService.reactToReply(treeId, replyId, request, token));
    }


}

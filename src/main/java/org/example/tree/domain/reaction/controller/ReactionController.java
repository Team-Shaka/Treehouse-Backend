package org.example.tree.domain.reaction.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.reaction.dto.ReactionRequestDTO;
import org.example.tree.domain.reaction.service.ReactionService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping("/trees/{treeId}/feed/posts/{postId}/reaction")
    public ApiResponse createPostReaction(
            @PathVariable Long treeId,
            @PathVariable Long postId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
            ) {
        String token = header.replace("Bearer ", "");
        reactionService.reactToPost(treeId, postId, request, token);
        return ApiResponse.onSuccess("");
    }

    @PostMapping("/trees/{treeId}/feed/comments/{commentId}/reaction")
    public ApiResponse createCommentReaction(
            @PathVariable Long treeId,
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
    ) {
        String token = header.replace("Bearer ", "");
        reactionService.reactToComment(treeId, commentId, request, token);
        return ApiResponse.onSuccess("");
    }

    @PostMapping("/trees/{treeId}/feed/replies/{replyId}/reaction")
    public ApiResponse createReplyReaction(
            @PathVariable Long treeId,
            @PathVariable Long replyId,
            @RequestHeader("Authorization") String header,
            @RequestBody ReactionRequestDTO.createReaction request
    ) {
        String token = header.replace("Bearer ", "");
        reactionService.reactToReply(treeId, replyId, request, token);
        return ApiResponse.onSuccess("");
    }


}

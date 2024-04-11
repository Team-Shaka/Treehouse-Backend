package org.example.tree.domain.reaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.reaction.dto.ReactionRequestDTO;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.service.ReactionService;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping("/trees/{treeId}/feed/posts/{postId}/reaction")
    @Operation(summary = "게시글 리액션", description = "게시글에 리액션을 추가합니다.")
    public CommonResponse<ReactionResponseDTO.addReaction> createPostReaction(
            @PathVariable Long treeId,
            @PathVariable Long postId,
            @RequestBody ReactionRequestDTO.createReaction request,
            @AuthMember @Parameter(hidden = true) Member member
            ) {
        return CommonResponse.onSuccess(reactionService.reactToPost(treeId, postId, request, member));
    }

    @PostMapping("/trees/{treeId}/feed/comments/{commentId}/reaction")
    @Operation(summary = "댓글 리액션", description = "댓글에 리액션을 추가합니다.")
    public CommonResponse<ReactionResponseDTO.addReaction> createCommentReaction(
            @PathVariable Long treeId,
            @PathVariable Long commentId,
            @RequestBody ReactionRequestDTO.createReaction request,
            @AuthMember @Parameter(hidden = true) Member member
    ) {
        return CommonResponse.onSuccess(reactionService.reactToComment(treeId, commentId, request, member));
    }

    @PostMapping("/trees/{treeId}/feed/replies/{replyId}/reaction")
    @Operation(summary = "답글 리액션", description = "답글에 리액션을 추가합니다.")
    public CommonResponse<ReactionResponseDTO.addReaction> createReplyReaction(
            @PathVariable Long treeId,
            @PathVariable Long replyId,
            @RequestBody ReactionRequestDTO.createReaction request,
            @AuthMember @Parameter(hidden = true) Member member
            ) {
        return CommonResponse.onSuccess(reactionService.reactToReply(treeId, replyId, request, member));
    }


}

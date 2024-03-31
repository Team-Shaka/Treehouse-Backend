package org.example.tree.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.converter.ReplyConverter;
import org.example.tree.domain.comment.dto.ReplyRequestDTO;
import org.example.tree.domain.comment.dto.ReplyResponseDTO;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.service.ReactionService;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyCommandService replyCommandService;
    private final ReplyQueryService replyQueryService;
    private final ReplyConverter replyConverter;
    private final CommentQueryService commentQueryService;
    private final ProfileService profileService;
    private final ReactionService reactionService;

    @Transactional
    public void createReply(Long treeId, Long commentId, ReplyRequestDTO.createReply request, Member member) {
        Profile profile = profileService.getTreeProfile(member, treeId);
        Comment comment = commentQueryService.findById(commentId);
        Reply reply = replyConverter.toReply(request.getContent(), profile, comment);
        replyCommandService.createReply(reply);
    }

    @Transactional
    public List<ReplyResponseDTO.getReply> getReplies(Comment comment) {
        List<Reply> replies = replyQueryService.getReplies(comment);
        return replies.stream()
                .map(reply -> {
                    List<ReactionResponseDTO.getReaction> reactions = reactionService.getReplyReactions(comment.getPost().getTree().getId(), reply.getId(), comment.getProfile());
                    return replyConverter.toGetReply(reply, reactions);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateReply(Long treeId, Long commentId, Long replyId, ReplyRequestDTO.updateReply request, Member member) {
        Profile profile = profileService.getTreeProfile(member, treeId);
        Comment comment = commentQueryService.findById(commentId);
        Reply reply = replyQueryService.findById(replyId);
        if (!reply.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        reply.updateReply(request.getContent());
    }

    @Transactional
    public void deleteReply(Long treeId, Long commentId, Long replyId, Member member) {
        Profile profile = profileService.getTreeProfile(member, treeId);
        Comment comment = commentQueryService.findById(commentId);
        Reply reply = replyQueryService.findById(replyId);
        if (!reply.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        replyCommandService.deleteReply(reply);
    }
}


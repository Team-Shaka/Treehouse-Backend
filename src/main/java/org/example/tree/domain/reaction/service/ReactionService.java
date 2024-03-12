package org.example.tree.domain.reaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.comment.service.CommentQueryService;
import org.example.tree.domain.comment.service.ReplyQueryService;
import org.example.tree.domain.notification.service.NotificationService;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.service.PostQueryService;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.reaction.converter.ReactionConverter;
import org.example.tree.domain.reaction.dto.ReactionRequestDTO;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.entity.Reaction;
import org.example.tree.domain.reaction.entity.ReactionType;
import org.example.tree.domain.reaction.entity.TargetType;
import org.example.tree.domain.reaction.repository.ReactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionCommandService reactionCommandService;
    private final ReactionQueryService reactionQueryService;
    private final PostQueryService postQueryService;
    private final CommentQueryService commentQueryService;
    private final ReplyQueryService replyQueryService;
    private final ProfileService profileService;
    private final ReactionConverter reactionConverter;
    private final ReactionRepository reactionRepository;
    private final NotificationService notificationService;

    @Transactional
    public ReactionResponseDTO.addReaction reactToPost(Long treeId, Long postId, ReactionRequestDTO.createReaction request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        Boolean isPushed =reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(postId, TargetType.POST, request.getType(), profile);
        if(isPushed){
            reactionCommandService.unReactToPost(postId, profile, request.getType());
            Integer countAfterUnreact = reactionQueryService.getReactionCount(postId, TargetType.POST, request.getType());
            return reactionConverter.toAddReaction(request.getType(), countAfterUnreact);
        }
        Reaction reaction = reactionConverter.toPostReaction(profile, postId, request.getType());
        Reaction savedReaction = reactionCommandService.reactToPost(reaction);
        Integer count = reactionQueryService.getReactionCount(postId, TargetType.POST, request.getType());
        notificationService.reactionNotification(profile, savedReaction, post.getProfile().getMember().getId());
        return reactionConverter.toAddReaction(request.getType(), count);
    }

    @Transactional
    public List<ReactionResponseDTO.getReaction> getPostReactions(Long treeId, Long postId, String token) {
        Map<ReactionType, Long> reactionSummary = reactionQueryService.getReactionSummary(postId, TargetType.POST);
        Profile profile = profileService.getTreeProfile(token, treeId);

        return reactionSummary.entrySet().stream().map(entry -> {
            ReactionType type = entry.getKey();
            Integer count = entry.getValue().intValue();
            Boolean isPushed = reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(postId, TargetType.POST, type, profile);

            return reactionConverter.toGetReaction(type, count, isPushed);
        }).collect(Collectors.toList());
    }

    @Transactional
    public ReactionResponseDTO.addReaction reactToComment(Long treeId, Long commentId, ReactionRequestDTO.createReaction request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Comment comment = commentQueryService.findById(commentId);
        Boolean isPushed =reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(commentId, TargetType.COMMENT, request.getType(), profile);
        if(isPushed){
            reactionCommandService.unReactToComment(commentId, profile, request.getType());
            Integer countAfterUnreact = reactionQueryService.getReactionCount(commentId, TargetType.COMMENT, request.getType());
            return reactionConverter.toAddReaction(request.getType(), countAfterUnreact);
        }
        Reaction reaction = reactionConverter.toCommentReaction(profile, commentId, request.getType());
        reactionCommandService.reactToPost(reaction);
        Integer count = reactionQueryService.getReactionCount(commentId, TargetType.COMMENT, request.getType());
        return reactionConverter.toAddReaction(request.getType(), count);
    }

    @Transactional
    public List<ReactionResponseDTO.getReaction> getCommentReactions(Long treeId, Long commentId, String token) {
        Map<ReactionType, Long> reactionSummary = reactionQueryService.getReactionSummary(commentId, TargetType.COMMENT);
        Profile profile = profileService.getTreeProfile(token, treeId);

        return reactionSummary.entrySet().stream().map(entry -> {
            ReactionType type = entry.getKey();
            Integer count = entry.getValue().intValue();
            Boolean isPushed = reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(commentId, TargetType.COMMENT, type, profile);

            return reactionConverter.toGetReaction(type, count, isPushed);
        }).collect(Collectors.toList());
    }

    @Transactional
    public ReactionResponseDTO.addReaction reactToReply(Long treeId, Long replyId, ReactionRequestDTO.createReaction request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Reply reply = replyQueryService.findById(replyId);
        Boolean isPushed =reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(replyId, TargetType.REPLY, request.getType(), profile);
        if(isPushed){
            reactionCommandService.unReactToReply(replyId, profile, request.getType());
            Integer countAfterUnreact = reactionQueryService.getReactionCount(replyId, TargetType.REPLY, request.getType());
            return reactionConverter.toAddReaction(request.getType(), countAfterUnreact);
        }
        Reaction reaction = reactionConverter.toReplyReaction(profile, replyId, request.getType());
        reactionCommandService.reactToPost(reaction);
        Integer count = reactionQueryService.getReactionCount(replyId, TargetType.REPLY, request.getType());
        return reactionConverter.toAddReaction(request.getType(), count);
    }

    @Transactional
    public List<ReactionResponseDTO.getReaction> getReplyReactions(Long treeId, Long replyId, Profile profile) {
        Map<ReactionType, Long> reactionSummary = reactionQueryService.getReactionSummary(replyId, TargetType.REPLY);

        return reactionSummary.entrySet().stream().map(entry -> {
            ReactionType type = entry.getKey();
            Integer count = entry.getValue().intValue();
            Boolean isPushed = reactionRepository.existsByTargetIdAndTargetTypeAndTypeAndProfile(replyId, TargetType.REPLY, type, profile);

            return reactionConverter.toGetReaction(type, count, isPushed);
        }).collect(Collectors.toList());
    }
}

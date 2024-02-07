package org.example.tree.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.converter.CommentConverter;
import org.example.tree.domain.comment.dto.CommentRequestDTO;
import org.example.tree.domain.comment.dto.CommentResponseDTO;
import org.example.tree.domain.comment.dto.ReplyResponseDTO;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.service.PostQueryService;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentService {
    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;
    private final PostQueryService postQueryService;
    private final ReplyService replyService;
    private final CommentConverter commentConverter;
    private final ProfileService profileService;

    @Transactional
    public void createComment(Long treeId, Long postId, CommentRequestDTO.createComment request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        Comment comment = commentConverter.toComment(request.getContent(), profile, post);
        post.increaseCommentCount();
        commentCommandService.createComment(comment);
    }

    @Transactional
    public List<CommentResponseDTO.getComment> getComments(Long treeId, Long postId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        List<Comment> comments = commentQueryService.getComments(post);
        return comments.stream()
                .map(comment -> {
                    List<ReplyResponseDTO.getReply> repliesForComment = replyService.getReplies(comment);
                    return commentConverter.toGetComment(comment, repliesForComment); // toGetComment 메서드 수정 필요
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long treeId, Long postId, Long commentId, CommentRequestDTO.updateComment request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        Comment comment = commentQueryService.findById(commentId);
        if (!comment.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        comment.updateComment(request.getContent());
    }

    @Transactional
    public void deleteComment(Long treeId, Long postId, Long commentId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        Comment comment = commentQueryService.findById(commentId);
        if (!comment.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        post.decreaseCommentCount();
        commentCommandService.deleteComment(comment);
    }
}

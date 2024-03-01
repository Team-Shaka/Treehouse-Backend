package org.example.tree.domain.comment.converter;

import org.example.tree.domain.comment.dto.CommentResponseDTO;
import org.example.tree.domain.comment.dto.ReplyResponseDTO;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.entity.Reaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentConverter {

    public Comment toComment(String content, Profile profile, Post post) {
        return Comment.builder()
                .content(content)
                .post(post)
                .profile(profile)
                .build();
    }

    public CommentResponseDTO.getComment toGetComment(Comment comment, List<ReactionResponseDTO.getReaction> reactions, List<ReplyResponseDTO.getReply> replies) {
        return CommentResponseDTO.getComment.builder()
                .commentId(comment.getId())
                .authorId(comment.getProfile().getId())
                .authorName(comment.getProfile().getMemberName())
                .content(comment.getContent())
                .reactions(reactions)
                .createdAt(comment.getCreatedAt())
                .replies(replies)
                .build();
    }
}

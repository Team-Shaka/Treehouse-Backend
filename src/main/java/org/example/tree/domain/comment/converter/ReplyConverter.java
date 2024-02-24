package org.example.tree.domain.comment.converter;

import org.example.tree.domain.comment.dto.ReplyResponseDTO;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.entity.Reaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReplyConverter {

    public Reply toReply(String content, Profile profile, Comment comment) {
        return Reply.builder()
                .content(content)
                .comment(comment)
                .profile(profile)
                .build();
    }

    public ReplyResponseDTO.getReply toGetReply(Reply reply, List<ReactionResponseDTO.getReaction> reactions) {
        return ReplyResponseDTO.getReply.builder()
                .replyId(reply.getId())
                .authorId(reply.getProfile().getId())
                .authorName(reply.getProfile().getMemberName())
                .content(reply.getContent())
                .reactions(reactions)
                .createdAt(reply.getCreatedAt())
                .build();
    }
}

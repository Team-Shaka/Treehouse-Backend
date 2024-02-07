package org.example.tree.domain.comment.converter;

import org.example.tree.domain.comment.dto.ReplyResponseDTO;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.comment.entity.Reply;
import org.example.tree.domain.profile.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ReplyConverter {

    public Reply toReply(String content, Profile profile, Comment comment) {
        return Reply.builder()
                .content(content)
                .comment(comment)
                .profile(profile)
                .build();
    }

    public ReplyResponseDTO.getReply toGetReply(Reply reply) {
        return ReplyResponseDTO.getReply.builder()
                .replyId(reply.getId())
                .memberName(reply.getProfile().getMemberName())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}

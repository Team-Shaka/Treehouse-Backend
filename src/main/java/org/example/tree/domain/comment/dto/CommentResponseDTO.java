package org.example.tree.domain.comment.dto;

import lombok.*;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class CommentResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getComment {
        private Long commentId;
        private Long authorId;
        private String authorName;
        private String content;
        private List<ReactionResponseDTO.getReaction> reactions;
        private LocalDateTime createdAt;
        private List<ReplyResponseDTO.getReply> replies;
    }
}

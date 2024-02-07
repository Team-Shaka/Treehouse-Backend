package org.example.tree.domain.comment.dto;

import lombok.*;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReply {
        private Long replyId;
        private String memberName;
        private String content;
        private List<ReactionResponseDTO.getReaction> reactions;
        private LocalDateTime createdAt;
    }
}

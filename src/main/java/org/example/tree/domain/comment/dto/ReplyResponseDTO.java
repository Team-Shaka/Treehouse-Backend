package org.example.tree.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

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
        private LocalDateTime createdAt;
    }
}

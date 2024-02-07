package org.example.tree.domain.comment.dto;

import lombok.*;

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
        private String memberName;
        private String content;
        private LocalDateTime createdAt;
        private List<ReplyResponseDTO.getReply> replies;
    }
}

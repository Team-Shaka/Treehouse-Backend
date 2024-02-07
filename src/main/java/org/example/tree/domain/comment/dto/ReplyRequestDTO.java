package org.example.tree.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyRequestDTO {

    @Getter
    public static class createReply {
        private String content;
    }

    @Getter
    public static class updateReply {
        private String content;
    }
}

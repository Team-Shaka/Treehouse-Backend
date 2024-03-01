package org.example.tree.domain.reaction.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ReactionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addReaction {
        private String type;
        private Integer count;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReaction {
        private String content;
        private Integer number;
        private Boolean isPushed;
    }
}

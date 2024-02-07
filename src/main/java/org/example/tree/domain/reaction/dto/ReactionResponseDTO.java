package org.example.tree.domain.reaction.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ReactionResponseDTO {
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

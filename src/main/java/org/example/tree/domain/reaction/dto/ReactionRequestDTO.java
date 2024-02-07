package org.example.tree.domain.reaction.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tree.domain.reaction.entity.ReactionType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ReactionRequestDTO {

    @Getter
    public static class createReaction {
        private ReactionType type;
    }
}

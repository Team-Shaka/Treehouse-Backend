package org.example.tree.domain.reaction.converter;

import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.entity.Reaction;
import org.example.tree.domain.reaction.entity.ReactionType;
import org.example.tree.domain.reaction.entity.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ReactionConverter {
    public Reaction toPostReaction(Profile profile, Long postId, ReactionType type) {
        return Reaction.builder()
                .profile(profile)
                .type(type)
                .targetId(postId)
                .targetType(TargetType.POST)
                .build();
    }

    public Reaction toCommentReaction(Profile profile, Long commentId, ReactionType type) {
        return Reaction.builder()
                .profile(profile)
                .type(type)
                .targetId(commentId)
                .targetType(TargetType.COMMENT)
                .build();
    }

    public Reaction toReplyReaction(Profile profile, Long replyId, ReactionType type) {
        return Reaction.builder()
                .profile(profile)
                .type(type)
                .targetId(replyId)
                .targetType(TargetType.REPLY)
                .build();
    }

    public ReactionResponseDTO.getReaction toGetReaction(ReactionType type, Integer count, Boolean isPushed) {
        return ReactionResponseDTO.getReaction.builder()
                .content(type.name())
                .number(count)
                .isPushed(isPushed)
                .build();
    }


}

package org.example.tree.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.entity.Reaction;
import org.example.tree.domain.reaction.entity.ReactionType;
import org.example.tree.domain.reaction.entity.TargetType;
import org.example.tree.domain.reaction.repository.ReactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionCommandService {

    private final ReactionRepository reactionRepository;
    public void reactToPost(Reaction reaction) {
        reactionRepository.save(reaction);
    }

    public void unReactToPost(Long postId, Profile profile, ReactionType type) {
        reactionRepository.deleteByTargetIdAndTargetTypeAndTypeAndProfile(postId, TargetType.POST, type, profile);
    }

    public void reactToComment(Reaction reaction) {
        reactionRepository.save(reaction);
    }

    public void unReactToComment(Long commentId, Profile profile, ReactionType type) {
        reactionRepository.deleteByTargetIdAndTargetTypeAndTypeAndProfile(commentId, TargetType.COMMENT, type, profile);
    }

    public void reactToReply(Reaction reaction) {
        reactionRepository.save(reaction);
    }

    public void unReactToReply(Long replyId, Profile profile, ReactionType type) {
        reactionRepository.deleteByTargetIdAndTargetTypeAndTypeAndProfile(replyId, TargetType.REPLY, type, profile);
    }


}

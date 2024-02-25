package org.example.tree.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.entity.Reaction;
import org.example.tree.domain.reaction.entity.ReactionType;
import org.example.tree.domain.reaction.entity.TargetType;
import org.example.tree.domain.reaction.repository.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionQueryService {

    private final ReactionRepository reactionRepository;

    public Map<ReactionType, Long> getReactionSummary(Long targetId, TargetType targetType) {
        // 유형별 반응 개수 집계
        List<Object[]> reactionCounts = reactionRepository.countReactionsByTypeAndTargetId(targetId, targetType);
        Map<ReactionType, Long> countsByType = reactionCounts.stream()
                .collect(Collectors.toMap(
                        row -> (ReactionType) row[0],
                        row -> (Long) row[1]));

        // 결과를 ReactionSummary 객체 또는 다른 DTO 형태로 가공하여 반환
        return countsByType;
    }

    public Integer getReactionCount(Long targetId, TargetType targetType, ReactionType type) {
        return reactionRepository.countReactionsByTypeAndTargetIdAndTargetType(type, targetId, targetType);
    }


}

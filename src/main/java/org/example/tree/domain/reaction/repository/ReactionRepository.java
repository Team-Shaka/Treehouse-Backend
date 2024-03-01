package org.example.tree.domain.reaction.repository;

import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.entity.Reaction;
import org.example.tree.domain.reaction.entity.ReactionType;
import org.example.tree.domain.reaction.entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findAllByTargetIdAndTargetType(Long postId, TargetType targetType);

    @Query("SELECT r.type AS type, COUNT(r) AS number FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType GROUP BY r.type")
    List<Object[]> countReactionsByTypeAndTargetId(@Param("targetId") Long targetId, @Param("targetType") TargetType targetType);

    Boolean existsByTargetIdAndTargetTypeAndTypeAndProfile(Long postId, TargetType targetType, ReactionType type, Profile profile);

    void deleteByTargetIdAndTargetTypeAndTypeAndProfile(Long postId, TargetType targetType, ReactionType type, Profile profile);

    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.type = :type")
    Integer countReactionsByTypeAndTargetIdAndTargetType(@Param("type") ReactionType type, @Param("targetId") Long targetId, @Param("targetType") TargetType targetType);

}

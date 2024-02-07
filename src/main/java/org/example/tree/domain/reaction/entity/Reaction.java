package org.example.tree.domain.reaction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.profile.entity.Profile;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType type; // 예: LIKE, DISLIKE 등

    private Long targetId; // 반응 대상의 ID

    @Enumerated(EnumType.STRING)
    private TargetType targetType; // 반응 대상 타입 (POST, COMMENT, REPLY)

    @JoinColumn(name = "profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile; // 반응한 회원
}

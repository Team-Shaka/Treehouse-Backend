package org.example.tree.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.entity.Tree;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberName; //트리에서 사용할 닉네임

    private String bio; //자기소개

    private String profileImageUrl; //프로필 이미지(트리 별로 상이)

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;
}

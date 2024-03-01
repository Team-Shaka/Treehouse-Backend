package org.example.tree.domain.branch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Branch extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer branchDegree;

    @JoinColumn(name = "rootId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile root;
    @JoinColumn(name = "leafId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile leaf;

    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;
}

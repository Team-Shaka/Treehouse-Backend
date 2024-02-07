package org.example.tree.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.profile.entity.Profile;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JoinColumn(name = "profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @JoinColumn(name = "commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public void updateReply(String content) {
        this.content = content;
    }
}

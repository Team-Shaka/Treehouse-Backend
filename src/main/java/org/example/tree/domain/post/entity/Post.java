package org.example.tree.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Integer reactionCount;

    @JoinColumn(name = "profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;

    @ElementCollection
    private List<String> postImages = new ArrayList<>();

    public void increaseReactionCount() {
        this.reactionCount++;
    }

    public void decreaseReactionCount() {
        if (this.reactionCount > 0){
            this.reactionCount--;
        }
    }

    public void updatePost(String content) {
        this.content = content;
    }


}

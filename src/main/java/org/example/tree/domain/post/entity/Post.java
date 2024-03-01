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

    private Integer commentCount;

    @JoinColumn(name = "profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    public void increaseReactionCount() {
        this.reactionCount++;
    }

    public void decreaseReactionCount() {
        if (this.reactionCount > 0){
            this.reactionCount--;
        }
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0){
            this.commentCount--;
        }
    }


    public void updatePost(String content) {
        this.content = content;
    }

    public void addPostImage(PostImage postImage) {
        this.postImages.add(postImage);
    }


}

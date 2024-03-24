package org.example.tree.domain.post.dto;

import lombok.*;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createPost {
        private Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getFeed {
        private Long treeId;
        private String treeName;
        private List<PostResponseDTO.getPost> posts;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPost {
        private Long postId;
        private Long authorId;
        private String profileImageUrl;
        private String memberName;
        private int branchDegree;
        private String content;
        private List<String> postImageUrls;
        private List<ReactionResponseDTO.getReaction> reactions;
        private Integer commentCount;
        private LocalDateTime createdAt;

    }


}

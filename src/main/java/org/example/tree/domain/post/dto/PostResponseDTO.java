package org.example.tree.domain.post.dto;

import lombok.*;

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
        private List<String> postImageUrls;
        private String writerId;
        private Long treeId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getFeed {

        private String profileImageUrl;
        private String memberName;
        private String content;
        private List<String> postImageUrls;
        private LocalDateTime createdAt;
        private Integer commentCount;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPost {
        private Long postId;
        private String profileImageUrl;
        private String memberName;
        private String content;
        private List<String> postImageUrls;
        private LocalDateTime createdAt;

    }


}

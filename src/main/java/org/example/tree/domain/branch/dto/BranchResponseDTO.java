package org.example.tree.domain.branch.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class BranchResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShortestPathResult{
        private int distance;
        private List<Long> path;
    }

    // Node 정보를 담을 클래스
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeDTO {
        private Long id;
        private String profileImageUrl;
        private String memberName;

        // 생성자, getter, setter 생략
    }

    // Link 정보를 담을 클래스
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkDTO {
        private Long sourceId;
        private Long targetId;

        // 생성자, getter, setter 생략
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class branchView{
        private List<NodeDTO> nodes;
        private List<LinkDTO> links;
        private Long startId;
        private Long endId;
    }
}

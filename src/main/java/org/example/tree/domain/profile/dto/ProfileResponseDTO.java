package org.example.tree.domain.profile.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createProfile {
        private Long userId;
        private Long treehouseId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getProfileDetails {
        private Long profileId;
        private List<Long> treeIds;
        private Long memberId;
        private String memberName;
        private String bio;
        private String profileImageUrl;
        private Integer branchDegree;
    }
}

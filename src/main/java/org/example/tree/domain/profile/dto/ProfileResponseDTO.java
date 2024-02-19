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
        private Long profileId;
        private Long treeId;
        private String memberName;
        private String profileImageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getProfileDetails {
        private Long profileId;
        private List<Long> treeIds;
        private String memberId;
        private String memberName;
        private String bio;
        private String profileImageUrl;
        private Integer branch;
    }
}

package org.example.tree.domain.profile.dto;

import lombok.*;

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
}

package org.example.tree.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileRequestDTO {
    @Getter
    public static class createProfile {
        private Long treeId;
        private String userId;
        private String memberName;
        private String profileImageUrl;
        private String bio;

    }
}

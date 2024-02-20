package org.example.tree.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileRequestDTO {
    @Getter
    public static class createProfile {
        private Long treeId;
        private String userId;
        private String memberName;
        private MultipartFile profileImage;
        private String bio;

    }

    @Getter
    public static class ownerProfile {
        private Long treeId;
        private String userId;
        private String memberName;
        private MultipartFile profileImage;
        private String bio;

    }
}

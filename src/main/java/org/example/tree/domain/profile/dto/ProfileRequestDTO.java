package org.example.tree.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileRequestDTO {
    @Getter
    public static class createProfile {
        private Long treehouseId;
        private String userName;
        private String memberName;
        private String profileImageURL;
        private String bio;

    }

    @Getter
    public static class ownerProfile {
        private Long treehouseId;
        private String userName;
        private String memberName;
        private String profileImageURL;
        private String bio;

    }
}

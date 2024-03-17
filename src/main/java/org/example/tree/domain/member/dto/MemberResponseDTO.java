package org.example.tree.domain.member.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkId {
        private Boolean isDuplicate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class registerMember {
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class reissue {
        private String accessToken;
        private String refreshToken;
    }
}

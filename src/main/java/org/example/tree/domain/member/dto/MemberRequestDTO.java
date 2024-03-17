package org.example.tree.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequestDTO {

    @Getter
    public static class checkId {
        private String userId;
    }

    @Getter
    public static class registerMember {
        private String phoneNumber;
        private String userId;
    }

    @Getter
    public static class reissue {
        private String refreshToken;
    }
}

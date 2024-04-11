package org.example.tree.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequestDTO {

    @Getter
    public static class checkName {
        private String userName;
    }

    @Getter
    public static class registerMember {
        private String phoneNumber;
        private String userName;
    }

    @Getter
    public static class loginMember {
        private String phoneNumber;
    }

    @Getter
    public static class reissue {

        @NotNull
        @NotBlank
        private String refreshToken;
    }

    @Getter
    public static class SmsRequestDto {
        @Override
        public String toString() {
            return "SmsRequestDto{" +
                    "targetPhoneNum='" + targetPhoneNum + '\'' +
                    '}';
        }

        private String targetPhoneNum;
    }

    @Getter
    public static class PhoneNumAuthDto {
        @Override
        public String toString() {
            return "PhoneNumAuthDto{" +
                    "phoneNum='" + phoneNum + '\'' +
                    ", authNum=" + authNum +
                    '}';
        }

        private String phoneNum;
        private Integer authNum;
    }
}

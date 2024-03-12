package org.example.tree.global.common.fcm.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class FCMRequestDTO {

    @Getter
    public static class sendFCM {
        private String title;
        private String message;
        private String receiverId;
    }

    @Getter
    public static class saveDevice {
        private String deviceUuid;
        private String fcmToken;
        private String userId;
    }
}

package org.example.tree.domain.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotification {
        private Long notificationId;
        private String senderName;
        private String senderProfileImageUrl;
        private String message;
        private String type;
        private Long treeId;
        private String treeName;
        private LocalDateTime createdAt;
    }
}

package org.example.tree.domain.notification.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tree.domain.notification.entity.NotificationType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class NotificationRequestDTO {

    @Getter
    public static class sendNotification {
        private String title;
        private String message;
        private NotificationType type;
        private Long receiverId;
    }
}

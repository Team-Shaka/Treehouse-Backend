package org.example.tree.domain.notification.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotification {
        private Long id;
        private String title;
        private String message;
        private String type;
    }
}

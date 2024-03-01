package org.example.tree.domain.notification.converter;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {

    public Notification toNotification(String title, String message, NotificationType type, Member receiver) {
        return Notification.builder()
                .title(title)
                .message(message)
                .type(type)
                .receiver(receiver)
                .build();
    }

    public NotificationResponseDTO.getNotification toGetNotification(Notification notification) {
        return NotificationResponseDTO.getNotification.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType().name())
                .build();
    }
}

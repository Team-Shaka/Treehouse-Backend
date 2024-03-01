package org.example.tree.domain.notification.converter;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.entity.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {

    public Notification toNotification(String title, String message, String type, Member receiver) {
        return Notification.builder()
                .title(title)
                .message(message)
                .type(NotificationType.valueOf(type))
                .receiver(receiver)
                .build();
    }
}

package org.example.tree.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.converter.NotificationConverter;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final NotificationConverter notificationConverter;

    public Notification sendNotification(String title, String message, String type, Member receiver) {
    }

    public List<Notification> getUserNotifications(String token) {
    }
}

package org.example.tree.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationCommandService {
    private final NotificationRepository notificationRepository;
    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}

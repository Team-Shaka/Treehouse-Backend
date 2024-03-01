package org.example.tree.domain.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.notification.converter.NotificationConverter;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.entity.NotificationType;
import org.example.tree.domain.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final NotificationConverter notificationConverter;
    private final MemberQueryService memberQueryService;

    @Transactional
    public void sendNotification(String title, String message, NotificationType type, String receiverId) {
        Member receiver = memberQueryService.findById(receiverId);
        Notification notification = notificationConverter.toNotification(title, message, type, receiver);
        notificationCommandService.createNotification(notification);
    }

    public List<NotificationResponseDTO.getNotification> getUserNotifications(String token) {
        Member member = memberQueryService.findByToken(token);
        List<Notification> notifications = notificationQueryService.getNotifications(member);
        return notifications.stream()
                .map(notificationConverter::toGetNotification)
                .collect(Collectors.toList());
    }

    public NotificationResponseDTO.getNotification getNotification(Long notificationId) {
        Notification notification = notificationQueryService.getNotification(notificationId);
        return notificationConverter.toGetNotification(notification);
    }
}

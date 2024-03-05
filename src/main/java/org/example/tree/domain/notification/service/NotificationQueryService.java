package org.example.tree.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.repository.NotificationRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {

    private final NotificationRepository notificationRepository;
    public List<Notification> getNotifications(Member member) {
        return notificationRepository.findAllUnreadByReceiver(member);
    }

    public Notification getNotification(Long notificationId) {
        return notificationRepository.findUnreadById(notificationId)
                .orElseThrow(()-> new GeneralException(GlobalErrorCode.NOTIFICATION_NOT_FOUND));
    }
}

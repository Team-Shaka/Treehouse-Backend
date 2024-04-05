package org.example.tree.domain.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.notification.converter.NotificationConverter;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.entity.NotificationType;
import org.example.tree.domain.notification.repository.NotificationRepository;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.entity.Reaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final NotificationConverter notificationConverter;
    private final MemberQueryService memberQueryService;

    @Transactional
    public void sendNotification(String title, String message, NotificationType type, Long receiverId) {
        Member receiver = memberQueryService.findById(receiverId);
        Notification notification = notificationConverter.toNotification(title, message, type, receiver);
        notificationCommandService.createNotification(notification);
    }

    @Transactional
    public void commentNotification(Profile sender, Comment comment, Long receiverId) {
        Member receiver = memberQueryService.findById(receiverId);
        Notification notification = notificationConverter.toCommentNotification(sender, comment, receiver);
        notificationCommandService.createNotification(notification);
    }

    @Transactional
    public void reactionNotification(Profile sender, Reaction reaction, Long receiverId) {
        Member receiver = memberQueryService.findById(receiverId);
        Notification notification = notificationConverter.toReactionNotification(sender, reaction, receiver);
        notificationCommandService.createNotification(notification);
    }

    @Transactional
    public void invitationNotification(Profile sender, Invitation invitation, Long receiverId) {
        Member receiver = memberQueryService.findById(receiverId);
        Notification notification = notificationConverter.toInvitationNotification(sender, invitation, receiver);
        notificationCommandService.createNotification(notification);
    }

    public List<NotificationResponseDTO.getNotification> getUserNotifications(Member member) {
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

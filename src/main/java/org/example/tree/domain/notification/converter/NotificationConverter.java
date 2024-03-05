package org.example.tree.domain.notification.converter;

import org.example.tree.domain.comment.entity.Comment;
import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.notification.dto.NotificationResponseDTO;
import org.example.tree.domain.notification.entity.Notification;
import org.example.tree.domain.notification.entity.NotificationType;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.entity.Reaction;
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

    public Notification toCommentNotification(Profile sender, Comment comment, Member receiver) {
        return Notification.builder()
                .title("댓글 알림")
                .message(sender.getMemberName() + "님이 게시물에 댓글을 남겼습니다. " + comment.getContent())
                .type(NotificationType.COMMENT)
                .receiver(receiver)
                .build();
    }

    public Notification toReactionNotification(Profile sender, Reaction reaction, Member receiver) {
        return Notification.builder()
                .title("반응 알림")
                .message(sender.getMemberName() + "님이 게시물에 " + reaction.getType() + " 반응을 남겼습니다.")
                .type(NotificationType.REACTION)
                .receiver(receiver)
                .build();
    }

    public Notification toInvitationNotification(Profile sender, Invitation invitation, Member receiver) {
        return Notification.builder()
                .title("초대 알림")
                .message(sender.getMemberName() + "님이 " + invitation.getTree().getName() + " 트리에 초대하였습니다.")
                .type(NotificationType.INVITATION)
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

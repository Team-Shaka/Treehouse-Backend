package org.example.tree.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tree.common.BaseDateTimeEntity;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;

    private String message;

    private boolean readStatus = false;

    private Long sourceId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Profile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver; // 알림을 받는 사용자
}

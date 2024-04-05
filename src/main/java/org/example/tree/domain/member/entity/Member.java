package org.example.tree.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName; //고유 문자열 아이디(인스타그램 st.)

    private String bio;

    private String phone; //전화번호

    private String profileImageUrl; //프로필 이미지

    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder.Default
    private Double activeRate = 0.0; //활동량
    @Builder.Default
    private Integer invitationCount = 5; //남아있는 초대장의 개수

    private LocalDateTime inactivatedAt; //탈퇴일자



    public void increaseInvitationCount() {
        this.invitationCount++;
    }
    public void decreaseInvitationCount() {
        if (this.invitationCount > 0) {
            this.invitationCount--;
        }
    }
}

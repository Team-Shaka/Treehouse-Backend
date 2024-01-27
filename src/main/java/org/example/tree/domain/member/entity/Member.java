package org.example.tree.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseDateTimeEntity {

    @Id
    private String id;
}

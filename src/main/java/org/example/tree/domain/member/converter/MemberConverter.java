package org.example.tree.domain.member.converter;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.MemberRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    public Member toMember (String id, String phone) {
        return Member.builder()
                .id(id)
                .phone(phone)
                .role(MemberRole.valueOf("ROLE_USER"))
                .build();
    }

    public MemberResponseDTO.registerMember toRegister(String accessToken, String refreshToken) {
        return MemberResponseDTO.registerMember.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

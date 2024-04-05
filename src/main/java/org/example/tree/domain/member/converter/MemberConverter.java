package org.example.tree.domain.member.converter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.MemberRole;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.member.service.MemberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    private final MemberQueryService memberQueryService;

    private static MemberQueryService staticMemberQueryService;

    @PostConstruct
    public void init() {
        this.staticMemberQueryService = this.memberQueryService;
    }

    /**
     * Security 어노테이션, AuthMember에서 사용함
     * @param id
     * @return Member
     */
    public static Member toMemberSecurity(String id){
        return staticMemberQueryService.findById(id);
    }

    public Member toMember (String id, String phone) {
        return Member.builder()
                .id(id)
                .phone(phone)
                .role(MemberRole.ROLE_USER)
                .build();
    }

    public MemberResponseDTO.checkId toCheckId(Boolean isDuplicated) {
        return MemberResponseDTO.checkId.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public MemberResponseDTO.registerMember toRegister(String accessToken, String refreshToken) {
        return MemberResponseDTO.registerMember.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public MemberResponseDTO.reissue toReissue(String accessToken, String refreshToken) {
        return MemberResponseDTO.reissue.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

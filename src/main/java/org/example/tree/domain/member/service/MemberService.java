package org.example.tree.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.invitation.service.InvitationQueryService;
import org.example.tree.domain.member.converter.MemberConverter;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.security.jwt.dto.TokenDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final InvitationQueryService invitationQueryService;

    private final MemberConverter memberConverter;

    @Transactional
    public MemberResponseDTO.checkId checkId(MemberRequestDTO.checkId request) {
        Optional<Member> optionalMember = memberQueryService.checkId(request.getUserId());
        Boolean isDuplicate = optionalMember.isPresent();
        return memberConverter.toCheckId(isDuplicate);
    }
    @Transactional
    public MemberResponseDTO.registerMember register(MemberRequestDTO.registerMember request) {
        Member member = memberConverter.toMember(request.getUserId(), request.getPhoneNumber());
        Member savedMember =memberCommandService.register(member);
        System.out.println("saved member = " + savedMember);
        TokenDTO savedToken = memberCommandService.login(savedMember);
        return memberConverter.toRegister(savedToken.getAccessToken(), savedToken.getRefreshToken());
    }

}

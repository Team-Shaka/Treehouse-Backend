package org.example.tree.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final MemberConverter memberConverter;

    @Transactional
    public MemberResponseDTO.checkId checkId(MemberRequestDTO.checkName request) {
        Optional<Member> optionalMember = memberQueryService.checkName(request.getUserName());
        Boolean isDuplicate = optionalMember.isPresent();
        return memberConverter.toCheckName(isDuplicate);
    }
    @Transactional
    public MemberResponseDTO.registerMember register(MemberRequestDTO.registerMember request) {
        Member member = memberConverter.toMember(request.getUserName(), request.getPhoneNumber());
        Member savedMember =memberCommandService.register(member);
        TokenDTO savedToken = memberCommandService.login(savedMember);
        return memberConverter.toRegister(savedMember, savedToken.getAccessToken(), savedToken.getRefreshToken());
    }

    @Transactional
    public MemberResponseDTO.reissue reissue(MemberRequestDTO.reissue request) {
        Member member = memberQueryService.findByToken(request.getRefreshToken());
        TokenDTO token = memberCommandService.reissue(member);
        return memberConverter.toReissue(token.getAccessToken(), token.getRefreshToken());
    }

}

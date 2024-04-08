package org.example.tree.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.converter.MemberConverter;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.redis.RefreshToken;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.redis.service.RedisService;
import org.example.tree.global.security.jwt.dto.TokenDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;

    private final RedisService redisService;

    @Transactional
    public MemberResponseDTO.checkName checkName(MemberRequestDTO.checkName request) {
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
    public MemberResponseDTO.registerMember login(MemberRequestDTO.loginMember request) {
        Member member = memberQueryService.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(() -> new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
        TokenDTO savedToken = memberCommandService.login(member);
        return memberConverter.toRegister(member, savedToken.getAccessToken(), savedToken.getRefreshToken());
    }

    @Transactional
    public MemberResponseDTO.reissue reissue(MemberRequestDTO.reissue request) {
        RefreshToken refreshToken = redisService.findRefreshToken(request.getRefreshToken()).orElseThrow(() -> new GeneralException(GlobalErrorCode.REFRESH_TOKEN_EXPIRED));
        Member member = memberQueryService.findById(refreshToken.getMemberId());
        TokenDTO token = memberCommandService.reissueToken(member,refreshToken);
        return memberConverter.toReissue(token.getAccessToken(), token.getRefreshToken());
    }

}

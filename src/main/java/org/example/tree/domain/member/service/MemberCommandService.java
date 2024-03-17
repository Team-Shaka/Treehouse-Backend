package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.domain.member.repository.RefreshTokenRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.security.jwt.RefreshToken;
import org.example.tree.global.security.jwt.TokenProvider;
import org.example.tree.global.security.jwt.dto.TokenDTO;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;




    public Member register(Member member) {
        return memberRepository.save(member);
    }
    public TokenDTO login(Member member) {

        String accessToken = tokenProvider.createAccessToken(member.getId());
        String rawToken = tokenProvider.createRefreshToken(member.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .token(rawToken)
                .build();
        refreshTokenRepository.save(refreshToken);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public TokenDTO reissue(Member member) {
        RefreshToken invalidToken = refreshTokenRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenRepository.delete(invalidToken);
        String accessToken = tokenProvider.createAccessToken(member.getId());
        String rawToken = tokenProvider.createRefreshToken(member.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .token(rawToken)
                .build();
        refreshTokenRepository.save(refreshToken);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }



}

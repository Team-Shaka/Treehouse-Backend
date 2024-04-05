package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.MemberRole;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.domain.member.repository.RefreshTokenRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.security.jwt.RefreshToken;
import org.example.tree.global.security.provider.TokenProvider;
import org.example.tree.global.security.jwt.dto.TokenDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;




    public Member register(Member member) {
        try {
            return memberRepository.save(member);
        }
        catch (Exception e){
            log.error("eerror");
            return null;
        }
    }
    public TokenDTO login(Member member) {

        String accessToken = tokenProvider.createAccessToken(String.valueOf(member.getId()), List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String rawToken = tokenProvider.createRefreshToken(String.valueOf(member.getId()));
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
        String accessToken = tokenProvider.createAccessToken(String.valueOf(member.getId()),List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String rawToken = tokenProvider.createRefreshToken(String.valueOf(member.getId()));
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

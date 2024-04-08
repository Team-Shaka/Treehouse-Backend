package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.MemberRole;
import org.example.tree.domain.member.entity.redis.RefreshToken;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.global.redis.service.RedisService;
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
    private final RedisService redisService;
    private final TokenProvider tokenProvider;




    public Member register(Member member) {
        return memberRepository.save(member);
    }
    public TokenDTO login(Member member) {

        String accessToken = tokenProvider.createAccessToken(member, List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String refreshToken = redisService.generateRefreshToken(member).getRefreshToken();
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDTO reissueToken(Member member, RefreshToken refreshToken) {

        String accessToken = tokenProvider.createAccessToken(member, List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String newRefreshToken = redisService.reGenerateRefreshToken(member, refreshToken).getRefreshToken();
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}

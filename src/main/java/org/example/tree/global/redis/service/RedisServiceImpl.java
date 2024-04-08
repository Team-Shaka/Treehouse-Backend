package org.example.tree.global.redis.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.MemberRole;
import org.example.tree.domain.member.entity.redis.RefreshToken;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.member.service.MemberService;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.exception.JwtAuthenticationException;
import org.example.tree.global.redis.repository.RefreshTokenRepository;
import org.example.tree.global.security.provider.TokenProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RedisServiceImpl implements RedisService{

    private final MemberQueryService memberQueryService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;

    /**
     * 리프레시 토큰 발급
     * @param member
     * @return
     */
    @Override
    public RefreshToken generateRefreshToken(Member member) {
        if (!memberQueryService.existById(member.getId()))
            throw new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND);

        String refreshToken = tokenProvider.createRefreshToken();

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(member.getId())
                        .refreshToken(refreshToken)
                        .build());
    }

    @Override
    public RefreshToken reGenerateRefreshToken(Member member, RefreshToken refreshToken) {

        tokenProvider.validateRefreshToken(refreshToken.getRefreshToken());
        refreshTokenRepository.delete(refreshToken);

        String newRefreshToken = tokenProvider.createRefreshToken();

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(member.getId())
                        .refreshToken(newRefreshToken)
                        .build());
    }

    @Override
    public Optional<RefreshToken> findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}

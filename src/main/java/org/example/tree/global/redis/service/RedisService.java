package org.example.tree.global.redis.service;

import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.entity.redis.RefreshToken;

import java.util.Optional;

public interface RedisService {

    RefreshToken generateRefreshToken(Member member);
    RefreshToken reGenerateRefreshToken(Member member,RefreshToken refreshToken);

    Optional<RefreshToken> findRefreshToken(String refreshToken);

}

package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.security.provider.TokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;
    private final TokenProvider jwtTokenProvider;

    public Optional<Member> checkId(String id) {
        return memberRepository.findById(id);
    }
    public Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(()->new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByPhoneNumber(String phone) {
        return memberRepository.findByPhone(phone);
    }

    public Member findByToken(String token) {
        // 토큰 검증
        if (!jwtTokenProvider.validateToken(token)) {
            throw new GeneralException(GlobalErrorCode.INVALID_TOKEN);
        }

        // 토큰을 사용하여 사용자 정보 가져오기
       String memberId = jwtTokenProvider.getMemberIdFromToken(token);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
        return member;
    }
}

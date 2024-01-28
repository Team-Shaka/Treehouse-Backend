package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(()->new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByPhoneNumber(String phone) {
        return memberRepository.findByPhone(phone);
    }
}

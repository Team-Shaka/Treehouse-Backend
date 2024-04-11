package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.repository.MemberRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.exception.MemberException;
import org.example.tree.global.security.provider.TokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Optional<Member> checkName(String userName) {
        return memberRepository.findByUserName(userName);
    }
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(()->new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }

    public Boolean existById(Long id){
        return memberRepository.existsById(id);
    }

    public Optional<Member> findByPhoneNumber(String phone) {
        return memberRepository.findByPhone(phone);
    }

    public void existByPhoneNumber(String phoneNum) {
        if (memberRepository.existsByPhone(phoneNum)) {
            throw new MemberException(GlobalErrorCode.PHONE_NUMBER_EXIST);
        }
    }
}

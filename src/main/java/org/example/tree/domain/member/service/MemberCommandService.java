package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
}

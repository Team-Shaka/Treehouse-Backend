package org.example.tree.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
}

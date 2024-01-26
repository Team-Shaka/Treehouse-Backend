package org.example.tree.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
}

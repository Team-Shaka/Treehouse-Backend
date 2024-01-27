package org.example.tree.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.service.MemberService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/checkId")
    public ApiResponse<MemberResponseDTO.checkId> checkId(
            @RequestBody final MemberRequestDTO.checkId request
    ) {
        return null;
    }
    @PostMapping("/register")
    public ApiResponse<MemberResponseDTO.registerMember> registerMember(
            @RequestBody final MemberRequestDTO.registerMember request
    ) {
        return null;
    }


}

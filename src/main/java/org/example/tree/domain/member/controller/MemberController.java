package org.example.tree.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.service.MemberService;
import org.example.tree.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/checkName")
    @Operation(summary = "아이디 중복 체크", description = "서비스에서 사용할 유저이름을 중복 체크합니다.")
    public ApiResponse<MemberResponseDTO.checkName> checkName(
            @RequestBody final MemberRequestDTO.checkName request
    ) {
        return ApiResponse.onSuccess(memberService.checkName(request));
    }
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    public ApiResponse<MemberResponseDTO.registerMember> registerMember(
            @RequestBody final MemberRequestDTO.registerMember request
    ) {
        return ApiResponse.onSuccess((memberService.register(request)));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    public ApiResponse<MemberResponseDTO.reissue> reissue(
            @RequestBody final MemberRequestDTO.reissue request
    ) {
        return ApiResponse.onSuccess(memberService.reissue(request));
    }
    @PostMapping("/login-tmp")
    @Operation(summary = "로그인 임시", description = "로그인 임시.")
    public ApiResponse<MemberResponseDTO.registerMember> loginTemp(
            @RequestBody final MemberRequestDTO.loginMember request
    ){
        return ApiResponse.onSuccess((memberService.login(request)));
    }
}

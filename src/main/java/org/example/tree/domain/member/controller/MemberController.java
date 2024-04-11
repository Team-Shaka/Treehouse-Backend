package org.example.tree.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.dto.MemberRequestDTO;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.service.MemberService;
import org.example.tree.global.common.CommonResponse;
import org.example.tree.global.feign.service.NcpService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;
    private final NcpService ncpService;


    @PostMapping("/checkName")
    @Operation(summary = "아이디 중복 체크", description = "서비스에서 사용할 유저이름을 중복 체크합니다.")
    public CommonResponse<MemberResponseDTO.checkName> checkName(
            @RequestBody final MemberRequestDTO.checkName request
    ) {
        return CommonResponse.onSuccess(memberService.checkName(request));
    }
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    public CommonResponse<MemberResponseDTO.registerMember> registerMember(
            @RequestBody final MemberRequestDTO.registerMember request
    ) {
        return CommonResponse.onSuccess((memberService.register(request)));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    public CommonResponse<MemberResponseDTO.reissue> reissue(
            @RequestBody final MemberRequestDTO.reissue request
    ) {
        return CommonResponse.onSuccess(memberService.reissue(request));
    }
    @PostMapping("/login-tmp")
    @Operation(summary = "로그인 임시", description = "로그인 임시.")
    public CommonResponse<MemberResponseDTO.registerMember> loginTemp(
            @RequestBody final MemberRequestDTO.loginMember request
    ){
        return CommonResponse.onSuccess((memberService.login(request)));
    }


    @Operation(summary = "인증번호 요청 API ✔️️", description = "인증번호 요청 API입니다. 대시(-) 제외 전화번호 입력하시면 됩니다. ex) 01012345678 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK 성공 , 인증번호 전송 완료"),
            @ApiResponse(responseCode = "NCP200_1", description = "OK 성공 , 이미 회원가입된 전화번호입니다."),

    })
    @PostMapping("/phone/sms")
    public CommonResponse<MemberResponseDTO.checkSentSms> sendSms(@RequestBody MemberRequestDTO.SmsRequestDto request) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        memberService.existsByPhoneNum(request.getTargetPhoneNum());
        MemberResponseDTO.checkSentSms authNumResultDto = ncpService.sendSms(request.getTargetPhoneNum());
        return CommonResponse.onSuccess(authNumResultDto);
    }

    //인증번호 검증
    @Operation(summary = "인증번호 검증 API ✔️️", description = "인증번호 검증 API입니다. 대시(-) 제외 전화번호와 인증번호 입력하시면 됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK 성공 , 인증 성공"),
            @ApiResponse(responseCode = "NCP400_1", description = "BAD_REQUEST, 전화번호를 잘못 전달했거나, 인증요청을 하지않은 상태로 확인버튼을 누른 경우"),
            @ApiResponse(responseCode = "NCP400_2", description = "BAD_REQUEST, 인증 번호가 옳지 않습니다."),
            @ApiResponse(responseCode = "NCP400_3", description = "BAD_REQUEST, 인증 시간(5분)이 지났습니다."),
    })
    @PostMapping("/phone/auth")
    public CommonResponse<MemberResponseDTO.checkPhoneAuth> authPhoneNum(@RequestBody MemberRequestDTO.PhoneNumAuthDto request) {
        MemberResponseDTO.checkPhoneAuth authNumResultDto = ncpService.authNumber(request.getAuthNum(), request.getPhoneNum());
        return CommonResponse.onSuccess(authNumResultDto);
    }
}

package org.example.tree.domain.root.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/health")
    @Operation(summary = "서버 상태 확인", description = "서버의 상태를 확인합니다.")
    public String healthCheck(){
        return "I'm healty!";
    }

    @GetMapping("/test")
    @Operation(summary = "access Token 유효시간, 인증여부 테스트", description = "테스트용.")
    public String testAccessToken(){return "test";}
}

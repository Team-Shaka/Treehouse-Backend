package org.example.tree.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
// 들어오는 요청 처리
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;


    /* 요청이 들어올 때마다 실행.
     * 토큰 확인, 토큰 유효성 검사, 토큰에 포함된 정보를 기반으로 인증 수행 */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // HTTP 요청에서 Authorization헤더를 찾아 토큰 반환
        String accessToken = tokenProvider.resolveToken(request, "Access");


        // 토큰이 있다면 진행
        if(accessToken != null) {
            if(!tokenProvider.validateToken(accessToken)){
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            // 토큰 유효 -> getUserInfoFromToken메서드를 사용해 JWT 토큰의 payload에서 정보 반환
            Claims info = tokenProvider.getUserInfoFromToken(accessToken);    // 토큰에서 user정보 가져옴(payload)

        }
        // 다음 단계 실행 -> 다른 필터 및 컨트롤러 실행
        filterChain.doFilter(request,response);
    }


    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        }
        return null;
    }


    // JWT 인증과 관련된 예외 처리
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        try {
            String json = new ObjectMapper().writeValueAsString(ApiResponse.onFailure(GlobalErrorCode.INVALID_TOKEN, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

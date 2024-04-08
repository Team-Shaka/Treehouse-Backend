package org.example.tree.global.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.exception.JwtAuthenticationException;
import org.example.tree.global.exception.JwtReissueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException authException) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            PrintWriter writer = response.getWriter();
            String errorCodeName = authException.getMessage();
            ApiResponse<String> apiErrorResult = ApiResponse.onFailure(GlobalErrorCode.valueOf(errorCodeName), "");

            writer.write(apiErrorResult.toString());
            writer.flush();
            writer.close();
        }
    }
}

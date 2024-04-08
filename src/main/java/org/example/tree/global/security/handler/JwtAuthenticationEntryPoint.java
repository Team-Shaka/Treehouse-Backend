package org.example.tree.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.exception.AuthErrorCode;
import org.example.tree.global.exception.GlobalErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);
        PrintWriter writer = response.getWriter();

        // AuthErrorCode.AUTHENTICATION_REQUIRED enum에서 코드와 메시지를 얻음
        String code = AuthErrorCode.AUTHENTICATION_REQUIRED.getCode();
        String message = AuthErrorCode.AUTHENTICATION_REQUIRED.getMessage();
        ApiResponse<String> apiErrorResult = ApiResponse.onFailure(code, message, null);

        writer.write(apiErrorResult.toString());
        writer.flush();
        writer.close();
    }
}

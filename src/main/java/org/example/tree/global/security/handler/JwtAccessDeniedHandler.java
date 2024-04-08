package org.example.tree.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tree.global.common.ApiResponse;
import org.example.tree.global.exception.AuthErrorCode;
import org.example.tree.global.exception.GlobalErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(403);
        PrintWriter writer = response.getWriter();

        // AuthErrorCode.AUTHENTICATION_DENIED enum에서 코드와 메시지를 얻음
        String code = AuthErrorCode.AUTHENTICATION_DENIED.getCode();
        String message = AuthErrorCode.AUTHENTICATION_DENIED.getMessage();
        ApiResponse<String> apiErrorResult = ApiResponse.onFailure(code, message, null);
        writer.write(apiErrorResult.toString());
        writer.flush();
        writer.close();
    }
}

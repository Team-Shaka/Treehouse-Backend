package org.example.tree.global.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtReissueException extends AuthenticationException {

    private String accessToken;

    private String refreshToken;

    public JwtReissueException(GlobalErrorCode code, String accessToken, String refreshToken) {
        super(code.name());
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

package org.example.tree.global.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(AuthErrorCode code) {
        super(code.name());
    }
}

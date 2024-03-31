package org.example.tree.global.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(GlobalErrorCode code) {
        super(code.name());
    }
}

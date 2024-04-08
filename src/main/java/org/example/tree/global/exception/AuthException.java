package org.example.tree.global.exception;

public class AuthException extends GeneralException{

    public AuthException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

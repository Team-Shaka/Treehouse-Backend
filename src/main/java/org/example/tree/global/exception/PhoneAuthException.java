package org.example.tree.global.exception;

public class PhoneAuthException extends GeneralException {
    public PhoneAuthException(BaseErrorCode errorCode){
        super(errorCode);
    }
}

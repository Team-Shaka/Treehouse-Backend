package org.example.tree.global.exception;

public class CustomFeignException extends GeneralException {
    public CustomFeignException(BaseErrorCode errorCode){
        super(errorCode);
    }
}

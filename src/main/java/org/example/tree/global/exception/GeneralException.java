package org.example.tree.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ErrorReasonDTO getErrorReason() {
        return this.errorCode.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.errorCode.getReasonHttpStatus();
    }
}

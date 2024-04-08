package org.example.tree.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode{

    // 500 Server Error
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "GLOBAL500_1", "서버 에러, 서버 개발자에게 알려주세요."),

    // Args Validation Error
    BAD_ARGS_ERROR(BAD_REQUEST, "GLOBAL400_1", "request body의 validation이 실패했습니다. 응답 body를 참고해주세요"),

    //  Member
    // 400 BAD_REQUEST - 잘못된 요청
    NOT_VALID_PHONE_NUMBER(BAD_REQUEST, "USER400_1", "유효하지 않은 전화번호 입니다."),

    // 401 Unauthorized - 권한 없음

    // 403 Forbidden - 인증 거부

    // 404 Not Found - 찾을 수 없음
    MEMBER_NOT_FOUND(NOT_FOUND, "USER404_1", "등록된 사용자 정보가 없습니다."),


    // 409 CONFLICT : Resource 를 찾을 수 없음
    DUPLICATE_PHONE_NUMBER(CONFLICT, "USER409_1", "중복된 전화번호가 존재합니다."),

    //Profile
    //404 Not Found - 찾을 수 없음
    PROFILE_NOT_FOUND(NOT_FOUND, "MEMBER404_1", "존재하지 않는 프로필입니다."),
    AVAILABLE_PROFILE_NOT_FOUND(NOT_FOUND, "MEMBER404_2", "현재 선택된 프로필이 없습니다."),

    //Treehouse
    //404 Not Found - 찾을 수 없음
    TREEHOUSE_NOT_FOUND(NOT_FOUND, "TREEHOUSE404_1", "존재하지 않는 트리입니다."),

    //Invitation
    //404 Not Found - 찾을 수 없음
    INVITATION_NOT_FOUND(NOT_FOUND, "INVITATION404_1", "존재하지 않는 초대장입니다."),

    //Post
    //404 Not Found - 찾을 수 없음
    POST_NOT_FOUND(NOT_FOUND, "POST404_1", "존재하지 않는 게시글입니다."),

    //Comment
    //404 Not Found - 찾을 수 없음
    COMMENT_NOT_FOUND(NOT_FOUND, "COMMENT404_1", "존재하지 않는 댓글입니다."),

    //Reply
    //404 Not Found - 찾을 수 없음
    REPLY_NOT_FOUND(NOT_FOUND, "REPLY404_1", "존재하지 않는 답글입니다."),

    //Branch
    //404 Not Found - 찾을 수 없음
    BRANCH_NOT_FOUND(NOT_FOUND, "BRANCH404_1", "브랜치 정보를 찾을 수 없습니다."),

    //Notification
    //404 Not Found - 찾을 수 없음
    NOTIFICATION_NOT_FOUND(NOT_FOUND, "NOTIFICATION", "존재하지 않는 알림입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .isSuccess(false)
                .build();
    }

}

package org.example.tree.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

    // Server Error
    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 에러, 서버 개발자에게 알려주세요."),

    // Args Validation Error
    BAD_ARGS_ERROR(BAD_REQUEST, "request body의 validation이 실패했습니다. 응답 body를 참고해주세요"),

    //  Member
    // 400 BAD_REQUEST - 잘못된 요청
    NOT_VALID_PHONE_NUMBER(BAD_REQUEST, "유효하지 않은 전화번호 입니다."),

    // 401 Unauthorized - 권한 없음
    INVALID_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    // 403 Forbidden - 인증 거부
    AUTHENTICATION_DENIED(FORBIDDEN, "인증이 거부 되었습니다."),
    AUTHENTICATION_REQUIRED(UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
    // 404 Not Found - 찾을 수 없음
    NEED_AGREE_REQUIRE_TERMS(NOT_FOUND, "필수 약관에 동의해 주세요."),
    MEMBER_NOT_FOUND(NOT_FOUND, "등록된 사용자 정보가 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "리프레시 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(NOT_FOUND, "리프레시 토큰이 만료 되었습니다."),
    TOKEN_EXPIRED(NOT_FOUND, "토큰이 만료 되었습니다."),
    // 409 CONFLICT : Resource 를 찾을 수 없음
    DUPLICATE_PHONE_NUMBER(CONFLICT, "중복된 전화번호가 존재합니다."),

    //Profile
    //404 Not Found - 찾을 수 없음
    PROFILE_NOT_FOUND(NOT_FOUND, "존재하지 않는 프로필입니다."),
    AVAILABLE_PROFILE_NOT_FOUND(NOT_FOUND, "현재 선택된 프로필이 없습니다."),

    //Tree
    //404 Not Found - 찾을 수 없음
    TREE_NOT_FOUND(NOT_FOUND, "존재하지 않는 트리입니다."),

    //Invitation
    //404 Not Found - 찾을 수 없음
    INVITATION_NOT_FOUND(NOT_FOUND, "존재하지 않는 초대장입니다."),

    //Post
    //404 Not Found - 찾을 수 없음
    POST_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시글입니다."),

    //Comment
    //404 Not Found - 찾을 수 없음
    COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 댓글입니다."),

    //Reply
    //404 Not Found - 찾을 수 없음
    REPLY_NOT_FOUND(NOT_FOUND, "존재하지 않는 답글입니다."),

    //Branch
    //404 Not Found - 찾을 수 없음
    BRANCH_NOT_FOUND(NOT_FOUND, "브랜치 정보를 찾을 수 없습니다."),

    //Notification
    //404 Not Found - 찾을 수 없음
    NOTIFICATION_NOT_FOUND(NOT_FOUND, "알림이 없습니다."),
    ;




    private final HttpStatus httpStatus;
    private final String message;
}

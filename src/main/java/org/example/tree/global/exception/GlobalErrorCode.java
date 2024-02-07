package org.example.tree.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
    //  Member
    // 400 BAD_REQUEST - 잘못된 요청
    NOT_VALID_PHONE_NUMBER(BAD_REQUEST, "유효하지 않은 전화번호 입니다."),

    // 401 Unauthorized - 권한 없음
    INVALID_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    AUTHENTICATION_REQUIRED(UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
    // 404 Not Found - 찾을 수 없음
    NEED_AGREE_REQUIRE_TERMS(NOT_FOUND, "필수 약관에 동의해 주세요."),
    MEMBER_NOT_FOUND(NOT_FOUND, "등록된 사용자 정보가 없습니다."),
    // 409 CONFLICT : Resource 를 찾을 수 없음
    DUPLICATE_PHONE_NUMBER(CONFLICT, "중복된 전화번호가 존재합니다."),

    //Profile
    //404 Not Found - 찾을 수 없음
    PROFILE_NOT_FOUND(NOT_FOUND, "존재하지 않는 프로필입니다."),

    //Tree
    //404 Not Found - 찾을 수 없음
    TREE_NOT_FOUND(NOT_FOUND, "존재하지 않는 트리입니다."),

    //Invitation
    //404 Not Found - 찾을 수 없음
    INVITATION_NOT_FOUND(NOT_FOUND, "존재하지 않는 초대장입니다."),

    //Post
    //404 Not Found - 찾을 수 없음
    POST_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시글입니다."),
    ;




    private final HttpStatus httpStatus;
    private final String message;
}

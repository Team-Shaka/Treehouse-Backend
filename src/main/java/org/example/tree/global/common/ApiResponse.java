package org.example.tree.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tree.global.exception.GlobalErrorCode;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonPropertyOrder( {"isSuccess", "code", "message", "data"} )
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("data")
    private T data;

    // 성공한 경우 응답 생성

    public static <T> ApiResponse<T> onSuccess(T data){
        return new ApiResponse<>(true, "200" , "요청에 성공하였습니다.", LocalDateTime.now(), data);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(GlobalErrorCode code, T data){
        return new ApiResponse<>(false, String.valueOf(code.getHttpStatus().value()), code.getMessage(), LocalDateTime.now(), data);
    }

}

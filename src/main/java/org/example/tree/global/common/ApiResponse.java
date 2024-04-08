package org.example.tree.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tree.global.exception.GlobalErrorCode;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonPropertyOrder( {"isSuccess", "code", "message", "data"} )
public class ApiResponse<T> {
    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Java 8 날짜/시간 모듈 등록
            mapper.registerModule(new JavaTimeModule());
            // 날짜와 시간을 ISO-8601 형식의 문자열로 직렬화
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 이쁘게 출력하기
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("data")
    private T data;

    // 성공한 경우 응답 생성

    public static <T> ApiResponse<T> onSuccess(T data){
        return new ApiResponse<>(true, "200" , "요청에 성공하였습니다.", LocalDateTime.now(), data);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false,code, message, LocalDateTime.now(), data);
    }

}

package com.avg.lawsuitmanagement.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    
    //500 발생
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 예외가 발생했습니다."),

    //JWT 관련 예외
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),
    
    EXCEPTION_AOP_TEST(HttpStatus.BAD_REQUEST, "TEST : 테스트용 예외가 발생했습니다.");

    private final HttpStatus httpStatus; // header에 담길 정보
    private final String message;
}

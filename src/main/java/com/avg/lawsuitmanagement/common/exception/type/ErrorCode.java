package com.avg.lawsuitmanagement.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    
    //500 발생
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 예외가 발생했습니다."),

    //valid exception
    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "입력값 형식이 잘못되었습니다."),

    //계정관련
    BAD_CREDENTIAL(HttpStatus.NOT_FOUND, "존재하지 않는 계정이거나 비밀번호가 틀렸습니다."),

    //JWT 관련 예외
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),

    //의뢰인 관련 예외

    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 의뢰인입니다."),

    //TEST
    EXCEPTION_AOP_TEST(HttpStatus.BAD_REQUEST, "TEST : 테스트용 예외가 발생했습니다.");

    private final HttpStatus httpStatus; // header에 담길 정보
    private final String message;
}

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
    PARAMETER_MISSING(HttpStatus.BAD_REQUEST, "필수값이 누락되었습니다."),

    //계정관련
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "존재하지 않는 계정이거나 비밀번호가 틀렸습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    //PROMOTION
    CLIENT_ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 가입된 의뢰인입니다."),
    PROMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "가입키가 존재하지 않습니다."),
    PROMOTION_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "만료된 가입키입니다."),

    //JWT 관련 예외
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),

    //파일 관련 예외
    FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패하였습니다."),
    FILE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패하였습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),

    //메일 관련 예외
    CANNOT_SEND_MAIL(HttpStatus.BAD_REQUEST, "메일을 전송할 수 없습니다."),
    TO_LIST_EMPTY(HttpStatus.BAD_REQUEST, "수신자 리스트가 비었습니다."),

    //의뢰인 관련 예외
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 의뢰인입니다."),
    CLIENT_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 등록된 의뢰인입니다."),
    CLIENT_NOT_FOUND_IN_LAWSUIT(HttpStatus.NOT_FOUND, "해당 사건에 존재하지 않는 의뢰인 입니다."),
    SIGNED_CLIENT_CANNOT_DELETE(HttpStatus.BAD_REQUEST, "회원가입한 의뢰인은 삭제할 수 없습니다."),
    CANNOT_ACCESS_CLOSING_LAWSUIT_CLIENT(HttpStatus.BAD_REQUEST, "종결된 사건의 의뢰인은 삭제할 수 없습니다."),

    //사원 관련 예외
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사원입니다."),
    MEMBER_NOT_ASSIGNED_TO_LAWSUIT(HttpStatus.NOT_FOUND, "해당 사건을 담당하는 사원이 아닙니다."),

    //사건 관련 예외
    LAWSUIT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사건입니다."),
    LAWSUIT_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상태입니다."),
    CANNOT_DELETE_SINGLE_EMPLOYEE_LAWSUIT(HttpStatus.BAD_REQUEST, "단일 담당자 사건은 삭제할 수 없습니다."),
    CANNOT_ACCESS_CLOSING_LAWSUIT(HttpStatus.BAD_REQUEST, "종결된 사건은 수정 및 삭제할 수 없습니다."),

    //상담 관련 예외
    ADVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상담입니다."),
    MEMBER_NOT_ASSIGNED_TO_ADVICE(HttpStatus.NOT_FOUND, "해당 상담을 담당하는 사원이 아닙니다."),
    ADVICE_MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 등록된 상담관입니다."),
    ADVICE_CLIENT_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 등록된 상담자입니다."),

    // 지출 관련 예외
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 지출내역입니다."),

    //직책, 역할, 법원
    HIERARCHY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 직책입니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 역할입니다."),
    COURT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 법원입니다."),

    // 채팅 관련 예외
    CHAT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청값입니다."),
    CHAT_ADD_FRIEND_MY_SELF(HttpStatus.BAD_REQUEST, "자신은 친구로 등록할 수 없습니다."),
    CHAT_ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "이미 친구로 등록되어 있습니다."),

    //DB
    DUPLICATE_KEY(HttpStatus.CONFLICT, "중복된 데이터가 있습니다."),
    //TEST
    EXCEPTION_AOP_TEST(HttpStatus.BAD_REQUEST, "TEST : 테스트용 예외가 발생했습니다.");

    private final HttpStatus httpStatus; // header에 담길 정보
    private final String message;
}

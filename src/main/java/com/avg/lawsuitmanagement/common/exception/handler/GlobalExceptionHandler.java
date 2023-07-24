package com.avg.lawsuitmanagement.common.exception.handler;

import com.avg.lawsuitmanagement.common.exception.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.dto.ExceptionDto;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 정의된 business exception에 대한 처리
     */
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ExceptionDto> customException(CustomRuntimeException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("CustomException 발생 : {} \n HttpStatus : {} \n Message : {} \n StackTrace : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), ex.getStackTrace());

        return new ResponseEntity<>(
            ExceptionDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build()
            , errorCode.getHttpStatus()
        );
    }

    /**
     * 핸들링되지 못한 exception에 대한 처리 (500, internal server error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> unknownException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNKNOWN_EXCEPTION;
        log.error("UnknownException 발생 : {} \n HttpStatus : {} \n Message : {} \n StackTrace : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), ex.getStackTrace());

        return new ResponseEntity<>(
            ExceptionDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build()
            , errorCode.getHttpStatus()
        );
    }
}

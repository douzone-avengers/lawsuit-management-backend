package com.avg.lawsuitmanagement.common.exception.handler;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.dto.ExceptionDto;
import com.avg.lawsuitmanagement.common.exception.dto.ValidException;
import com.avg.lawsuitmanagement.common.exception.dto.ValidExceptionDto;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
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
        log.error(
            "CustomException 발생 : {} \n HttpStatus : {} \n Message : {} \n ExceptionDetail : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), ex.toString());

        return new ResponseEntity<>(
            ExceptionDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build()
            , errorCode.getHttpStatus()
        );
    }

    /**
     * ModelAttribute(param) 일 때 형식 예외에 대한 처리
     * RequestBody 일 때 형식 위반시 MethodArgumentNotValidException 이 발생한다.
     * 그런데 BindException은 MethodArgumentNotValidExceptiond의 부모이므로 따로 처리할 필요가 없다.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDto> bindException(BindException ex) {
        ErrorCode errorCode = ErrorCode.VALID_EXCEPTION;

        List<ValidException> validExceptions = ex.getBindingResult().getFieldErrors().stream().map(
            x -> ValidException.builder()
                .filed(x.getField())
                .filedMessage(x.getDefaultMessage()).build()
        ).toList();

        log.error(
            "BindException 발생 : {} \n HttpStatus : {} \n Message : {} \n ValidException : {} \n ExceptionDetail : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), validExceptions, ex.toString());

        return new ResponseEntity<>(
            ValidExceptionDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .validExceptions(validExceptions)
                .build(),
            errorCode.getHttpStatus()
        );
    }

    /**
     * 로그인 실패 시
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> badCredentialsException(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.BAD_CREDENTIAL;
        log.error(
            "BadCredentialsException 발생 : {} \n HttpStatus : {} \n Message : {} \n ExceptionDetail : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), ex.toString());

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
        log.error(
            "UnknownException 발생 : {} \n HttpStatus : {} \n Message : {} \n ExceptionDetail : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), ex.toString());

        return new ResponseEntity<>(
            ExceptionDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build()
            , errorCode.getHttpStatus()
        );
    }
}

package com.avg.lawsuitmanagement.common.exception.handler;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.dto.ExceptionDto;
import com.avg.lawsuitmanagement.common.exception.dto.ValidException;
import com.avg.lawsuitmanagement.common.exception.dto.ValidExceptionDto;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
     * RequestBody 등에서 유효하지 않은 파라미터일 경우 예외처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> methodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.VALID_EXCEPTION;
        log.error(
            "methodArgumentNotValidException 발생 : {} \n HttpStatus : {} \n Message : {} \n ExceptionDetail : {}",
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
     * 필수값 누락에 대한 예외처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDto> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorCode errorCode = ErrorCode.PARAMETER_MISSING;

        String missingParamName = ex.getParameterName();
        String errorMessage = String.format("필수값 이 누락되었습니다. 누락된 필드 : '%s'", missingParamName);

        log.error(
            "MissingServletRequestParameterException 발생 : {} \n HttpStatus : {} \n Message : {} \n Missing Parameter : {} \n ExceptionDetail : {}",
            errorCode.name(), errorCode.getHttpStatus().toString(),
            errorCode.getMessage(), missingParamName, ex.toString());

        return new ResponseEntity<>(
            ExceptionDto.builder()
                .code(errorCode.name())
                .message(errorMessage)
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
     * 인가 관련 예외처리(권한 없을 시)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> accessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        log.error(
            "AccessDeniedException 발생 : {} \n HttpStatus : {} \n Message : {} \n ExceptionDetail : {}",
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

package com.avg.lawsuitmanagement.common.custom;

import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomRuntimeException extends RuntimeException {

    private final ErrorCode errorCode;
}

package com.avg.lawsuitmanagement.common.exception.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class ExceptionDto {
    private String code;
    private String message;



}

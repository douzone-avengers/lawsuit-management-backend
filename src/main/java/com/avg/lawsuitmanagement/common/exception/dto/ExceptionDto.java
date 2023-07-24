package com.avg.lawsuitmanagement.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private String code;
    private String message;



}

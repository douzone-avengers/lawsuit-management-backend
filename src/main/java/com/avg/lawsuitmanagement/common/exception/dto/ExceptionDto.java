package com.avg.lawsuitmanagement.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionDto {
    private String code;
    private String message;



}

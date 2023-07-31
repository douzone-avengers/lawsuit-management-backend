package com.avg.lawsuitmanagement.common.exception.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ValidExceptionDto extends ExceptionDto {
    List<ValidException> validExceptions;
}


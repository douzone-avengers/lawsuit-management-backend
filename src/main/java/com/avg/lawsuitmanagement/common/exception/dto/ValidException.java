package com.avg.lawsuitmanagement.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ValidException {
    String filed;
    String filedMessage;
}

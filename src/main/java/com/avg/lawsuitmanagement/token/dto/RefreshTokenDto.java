package com.avg.lawsuitmanagement.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class RefreshTokenDto {
    private String key; //이메일
    private String value; // refreshToken
}
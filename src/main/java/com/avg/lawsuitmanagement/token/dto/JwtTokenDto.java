package com.avg.lawsuitmanagement.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;
}
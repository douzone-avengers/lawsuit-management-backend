package com.avg.lawsuitmanagement.token.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenParam {
    private String key;
    private String value;
}